package com.app.frame;

import com.app.server.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class LoginFrame extends JFrame implements Runnable {
    public static Thread mainForm;
    public static Thread adminForm;
    private static Socket socket;
    private static ObjectOutputStream output;
    private static ObjectInputStream input;
    Container container = getContentPane();
    final JLabel titleLabel = new JLabel("Авторизация");
    final JLabel userLabel = new JLabel("Пользователь: ");
    final JLabel passwordLabel = new JLabel("Пароль: ");
    final JTextField userTextField = new JTextField();
    final JPasswordField passwordTextField = new JPasswordField();
    final JButton loginButton = new JButton("Вход");
    final JButton resetButton = new JButton("Регистрация");
    final JButton adminButton = new JButton("Вход как администратор");
    final Color buttonColor = new Color(70, 130, 180);
    final Color buttonTextColor = new Color(245, 255, 250);
    public LoginFrame()
    {
        setLayoutManager();
        setLocationAndSize();
        action();
        addComponentsToContainer();
        setTitle("Login Form");
        setVisible(true);
        setSize(380,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    public void setLayoutManager()
    {
        container.setLayout(null);
    }
    public void setLocationAndSize()
    {
        Font font = new Font("serif", Font.PLAIN, 24);
        titleLabel.setBounds(115, 35, 150, 50);
        titleLabel.setFont(font);
        userLabel.setBounds(50,100,100,30);
        passwordLabel.setBounds(50,170,100,30);
        userTextField.setBounds(150,100,150,30);
        passwordTextField.setBounds(150,170,150,30);
        loginButton.setBounds(50,250,120,30);
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(buttonTextColor);
        resetButton.setBounds(180,250,120,30);
        resetButton.setBackground(buttonColor);
        resetButton.setForeground(buttonTextColor);
        adminButton.setBounds(50, 300, 250, 30);
        adminButton.setBackground(buttonColor);
        adminButton.setForeground(buttonTextColor);
    }
    public void action(){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==loginButton){
                    if(!userTextField.getText().equals("") && passwordTextField.getPassword().length >= 4 && passwordTextField.getPassword().length < 16) {
                        String pwd = new String(passwordTextField.getPassword());
                        boolean auth = DBConnection.userAuthorization(userTextField.getText(), pwd);
                        if (auth) {
                            setVisible(false);
                        }
                    }
                }
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==resetButton){
                    new Thread(new RegisterFrame()).start();
                }
            }
        });
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==adminButton){
                    if (!userTextField.getText().equals("") && passwordTextField.getPassword().length != 0) {
                        if (userTextField.getText().equals("Анна") && new String(passwordTextField.getPassword()).equals("12345")) {
                            adminForm = new Thread(new AdminFrame());
                            adminForm.start();
                        } else JOptionPane.showMessageDialog(null, "Нет необходимых полномочий!");
                    }
                    else JOptionPane.showMessageDialog(null, "Заполните поля ввода!");
                }
            }
        });
    }
    public void addComponentsToContainer()
    {
        container.add(titleLabel);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordTextField);
        container.add(loginButton);
        container.add(resetButton);
        container.add(adminButton);
    }

    @Override
    public void run(){
        try {
            while (true) {
                socket = new Socket(InetAddress.getByName("127.0.0.1"), 5678);
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                JOptionPane.showMessageDialog(null, (String)input.readObject());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
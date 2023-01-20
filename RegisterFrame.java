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

public class RegisterFrame extends JFrame implements ActionListener, Runnable {
    static private Socket socket;
    static private ObjectOutputStream output;
    static private ObjectInputStream input;
    Container container = getContentPane();
    final JLabel titleLabel = new JLabel("Регистрация");
    final JLabel userLabel = new JLabel("Введите фамилию семьи: ");
    final JLabel passwordLabel = new JLabel("Придумайте пароль: ");
    final JLabel passwordRepeatLabel = new JLabel("Повторите пароль: ");
    final JTextField userTextField = new JTextField();
    final JPasswordField passwordTextField = new JPasswordField();
    final JPasswordField passwordRepeatTextField = new JPasswordField();
    final JButton resetButton = new JButton("Зарегистрироваться");
    final Color buttonColor = new Color(70, 130, 180);
    final Color buttonTextColor = new Color(245, 255, 250);
    public RegisterFrame()
    {
        setLayoutManager();
        setLocationAndSize();
        action();
        addComponentsToContainer();
        setTitle("Register Form");
        setVisible(true);
        setSize(450,400);
        setLocationRelativeTo(null);
   //     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    public void setLayoutManager()
    {
        container.setLayout(null);
    }
    public void setLocationAndSize()
    {
        Font font = new Font("serif", Font.PLAIN, 24);
        titleLabel.setBounds(125, 35, 150, 50);
        titleLabel.setFont(font);
        userLabel.setBounds(50,100,180,30);
        passwordLabel.setBounds(50,150,180,30);
        passwordRepeatLabel.setBounds(50, 200, 180, 30);
        userTextField.setBounds(250,100,150,30);
        passwordTextField.setBounds(250,150,150,30);
        passwordRepeatTextField.setBounds(250, 200, 150, 30);
        resetButton.setBounds(100,250,180,30);
        resetButton.setBackground(buttonColor);
        resetButton.setForeground(buttonTextColor);
    }
    public void action(){
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==resetButton){
                    if(!userTextField.getText().equals("") && passwordTextField.getPassword().length != 0 && passwordRepeatTextField.getPassword().length != 0){
                        if (passwordTextField.getPassword().length > 4 && passwordTextField.getPassword().length < 16) {
                            if (new String(passwordTextField.getPassword()).equals(new String(passwordRepeatTextField.getPassword()))) {
                                boolean r = DBConnection.userRegistration(userTextField.getText(), new String(passwordTextField.getPassword()));
                                if (r){
                                    setVisible(false);
                                }
                            }
                            else JOptionPane.showMessageDialog(new JOptionPane(), "Пароли не совпадают!");
                        }
                        else JOptionPane.showMessageDialog(new JOptionPane(), "Пароль должен быть от 5 до 15 символов!!!");
                    }
                    else JOptionPane.showMessageDialog(new JOptionPane(), "Ошибка! Заполните поля ввода!");
                }
            }
        });
    }
    public void addComponentsToContainer()
    {
        container.add(titleLabel);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(passwordRepeatLabel);
        container.add(userTextField);
        container.add(passwordTextField);
        container.add(passwordRepeatTextField);
        container.add(resetButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    @Override
    public void run(){
        try {
            while (true){
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

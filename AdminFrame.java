package com.app.frame;

import com.app.server.DBConnection;
import com.db.connection.DBMethods;
import com.home.bookkeeping.ItemOfExpenditure;
import com.home.bookkeeping.SourceOfIncome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class AdminFrame extends JFrame implements Runnable {
    static private Socket socket;
    static private ObjectOutputStream output;
    static private ObjectInputStream input;
    Container container = getContentPane();
    final JLabel titleLabel = new JLabel("Меню администратора");
    DefaultListModel incomeModel = new DefaultListModel();
    DefaultListModel expenditureModel = new DefaultListModel();
    JList incomeList = new JList(incomeModel);
    JList expenditureList = new JList(expenditureModel);
    final JLabel incomeLabel = new JLabel("Возможные источники дохода:");
    final JLabel expenditureLabel = new JLabel("Возможные статьи расхода:");
    final JButton incomeAddButton = new JButton("Добавить");
    final JButton incomeDeleteButton = new JButton("Удалить");
    final JButton incomeEditButton1 = new JButton("Редактировать");
    final JButton incomeEditButton = new JButton("Отредактировать");
    final JTextField incomeTextField = new JTextField();
    final JButton expenditureAddButton = new JButton("Добавить");
    final JButton expenditureDeleteButton = new JButton("Удалить");
    final JButton expenditureEditButton1 = new JButton("Редактировать");
    final JButton expenditureEditButton = new JButton("Отpедактировать");
    final JTextField expenditureTextField = new JTextField();
    final Color buttonColor = new Color(70, 130, 180);
    final Color buttonTextColor = new Color(245, 255, 250);
    public AdminFrame()
    {
        setLayoutManager();
        setLocationAndSize();
        action();
        addComponentsToContainer();
        setTitle("Admin Form");
        setVisible(true);
        setSize(570,500);
        setLocationRelativeTo(null);
 //       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    public void setLayoutManager()
    {
        container.setLayout(null);
    }
    public void setLocationAndSize()
    {
        Font font = new Font("serif", Font.PLAIN, 24);
        titleLabel.setBounds(140, 15, 240, 50);
        titleLabel.setFont(font);
        incomeLabel.setBounds(50, 70, 200, 30);
        incomeList.setBounds(50, 100, 200, 200);
        incomeAddButton.setBounds(75, 315, 150, 20);
        incomeAddButton.setBackground(buttonColor);
        incomeAddButton.setForeground(buttonTextColor);
        incomeDeleteButton.setBounds(75, 350, 150, 20);
        incomeDeleteButton.setBackground(buttonColor);
        incomeDeleteButton.setForeground(buttonTextColor);
        incomeEditButton1.setBounds(75, 385, 150, 20);
        incomeEditButton1.setBackground(buttonColor);
        incomeEditButton1.setForeground(buttonTextColor);
        incomeEditButton.setBounds(75, 385, 150, 20);
        incomeEditButton.setVisible(false);
        incomeEditButton.setBackground(buttonColor);
        incomeEditButton.setForeground(buttonTextColor);
        incomeTextField.setBounds(75, 415, 150, 25);
        if (SourceOfIncome.getIncomeList().isEmpty())
            DBMethods.setIncomeList();
        if (ItemOfExpenditure.getExpenditureList().size() == 0)
            DBMethods.setExpensesList();
        incomeModel.clear();
        for(var inc: SourceOfIncome.getIncomeList()) {
            incomeModel.addElement(inc);
        }
        expenditureModel.clear();
        for(var exp: ItemOfExpenditure.getExpenditureList()) {
            expenditureModel.addElement(exp);
        }
        expenditureLabel.setBounds(300, 70, 200, 30);
        expenditureList.setBounds(300, 100, 200, 200);
        expenditureAddButton.setBounds(325, 315, 150, 20);
        expenditureAddButton.setBackground(buttonColor);
        expenditureAddButton.setForeground(buttonTextColor);
        expenditureDeleteButton.setBounds(325, 350, 150, 20);
        expenditureDeleteButton.setBackground(buttonColor);
        expenditureDeleteButton.setForeground(buttonTextColor);
        expenditureEditButton1.setBounds(325, 385, 150, 20);
        expenditureEditButton1.setBackground(buttonColor);
        expenditureEditButton1.setForeground(buttonTextColor);
        expenditureEditButton.setBounds(325, 385, 150, 20);
        expenditureEditButton.setBackground(buttonColor);
        expenditureEditButton.setForeground(buttonTextColor);
        expenditureEditButton.setVisible(false);
        expenditureDeleteButton.setBackground(buttonColor);
        expenditureDeleteButton.setForeground(buttonTextColor);
        expenditureTextField.setBounds(325, 415,150, 25);
    }
    public void updateInfo(){
        setVisible(false);
        new AdminFrame();
    }
    public void action(){
        incomeAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==incomeAddButton){
                    if (!incomeTextField.getText().equals("")){
                        DBConnection.addSourceIncome(incomeTextField.getText());
                    }
                    incomeTextField.setText("");
                    updateInfo();
                }
            }
        });
        incomeDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==incomeDeleteButton){
                    if (!incomeList.isSelectionEmpty()){
                        DBConnection.deleteSourceIncome(incomeList.getSelectedValue().toString());
                        updateInfo();
                    }
                    else JOptionPane.showMessageDialog(null, "Выберите элемент");
                }
            }
        });
        incomeEditButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==incomeEditButton1){
                    if (!incomeList.isSelectionEmpty()){
                        String index = incomeList.getSelectedValue().toString();
                        incomeTextField.setText(index);
                        incomeEditButton1.setVisible(false);
                        incomeEditButton.setVisible(true);
                        incomeEditButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (e.getSource()==incomeEditButton){
                                    DBConnection.editSourceIncome(incomeTextField.getText(), index);
                                    incomeTextField.setText("");
                                    incomeEditButton.setVisible(false);
                                    incomeEditButton1.setVisible(true);
                                    updateInfo();
                                }
                            }
                        });
                    }
                    else JOptionPane.showMessageDialog(null, "Выберите элемент");
                }
            }
        });

        expenditureAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==expenditureAddButton){
                    if (!expenditureTextField.getText().equals("")){
                        DBConnection.addItemExpenditure(expenditureTextField.getText());
                        expenditureTextField.setText("");
                        updateInfo();
                    }
                }
            }
        });
        expenditureDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==expenditureDeleteButton){
                    if (!expenditureList.isSelectionEmpty()){
                        DBConnection.deleteItemExpenditure(expenditureList.getSelectedValue().toString());
                        updateInfo();
                    }
                    else JOptionPane.showMessageDialog(null, "Выберите элемент");
                }
            }
        });
        expenditureEditButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==expenditureEditButton1){
                    if (!expenditureList.isSelectionEmpty()){
                        String index = expenditureList.getSelectedValue().toString();
                        expenditureTextField.setText(index);
                        expenditureEditButton1.setVisible(false);
                        expenditureEditButton.setVisible(true);
                        expenditureEditButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (e.getSource()==expenditureEditButton){
                                    DBConnection.editItemExpenditure(expenditureTextField.getText(), index);
                                    expenditureEditButton.setVisible(false);
                                    expenditureEditButton1.setVisible(true);
                                    expenditureTextField.setText("");
                                    updateInfo();
                                }
                            }
                        });
                    }
                    else JOptionPane.showMessageDialog(null, "Выберите элемент");
                }
            }
        });
    }
    public void addComponentsToContainer()
    {
        container.add(titleLabel);
        container.add(incomeList);
        container.add(expenditureList);
        container.add(incomeLabel);
        container.add(incomeAddButton);
        container.add(incomeDeleteButton);
        container.add(incomeEditButton1);
        container.add(incomeEditButton);
        container.add(incomeTextField);
        container.add(expenditureLabel);
        container.add(expenditureAddButton);
        container.add(expenditureDeleteButton);
        container.add(expenditureEditButton);
        container.add(expenditureEditButton1);
        container.add(expenditureTextField);
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

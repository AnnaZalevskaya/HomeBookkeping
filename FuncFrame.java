package com.app.frame;

import com.app.server.DBConnection;
import com.db.connection.DBMethods;
import com.home.bookkeeping.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class FuncFrame extends JFrame implements Runnable{
    static private Socket socket;
    static private ObjectOutputStream output;
    static private ObjectInputStream input;
    Container container = getContentPane();
    Font fontTitle = new Font("serif", Font.PLAIN, 20);
    //добавление пользователя
    final JLabel userLabelTitle = new JLabel("Добавление члена семьи");
    final JLabel nameLabel = new JLabel("Имя пользователя");
    final JLabel dateOfBirthLabel = new JLabel("Дата рождения");
    final JLabel degreeOfKinshipLabel = new JLabel("Степень родства");
    final JLabel personalBudgetLabel = new JLabel("Персональный бюджет");
    final JButton addUserButton = new JButton("Добавить");
    final JTextField nameTextField = new JTextField();
    final JTextField dateOfBirthTextField = new JTextField();
    final JTextField degreeOfKinshipTextField = new JTextField();
    final JTextField personalBudgetTextField = new JTextField();
    //доходы
    final JLabel incomeLabelTitle = new JLabel("Добавление дохода");
    final JLabel famMemberLabel = new JLabel("Член семьи");
    final JLabel dateIncomeLabel = new JLabel("Дата дохода");
    final JLabel incomeSourceLabel = new JLabel("Источник дохода");
    final JLabel amountLabel = new JLabel("Сумма");
    final JLabel commentLabel = new JLabel("Комментарий");
    final JTextField dateTextField = new JTextField();
    final JTextField amountTextField = new JTextField();
    final JTextField commentTextField = new JTextField();
    //расходы
    final JLabel expenditureLabelTitle = new JLabel("Добавление расхода");
    final JLabel dateExpenditureLabel = new JLabel("Дата расхода");
    final JLabel expenditureSourceLabel = new JLabel("Статья расхода");
    final JButton addIncomeButton = new JButton("Добавить");
    final JButton addExpenditureButton = new JButton("Добавить");
    JComboBox famBox;
    JComboBox incomeBox;
    JComboBox expenditureBox;

    public FuncFrame(){}
    public FuncFrame(int i){
        switch (i){
            case 1:
                income();
                break;
            case 2:
                expenditure();
                break;
            case 3:
                addUser();
                break;
        }
    }
    public void setLayoutManager()
    {
        container.setLayout(null);
    }
    public void setLocationAndAddEl(){
        amountLabel.setBounds(50,200,100,30);
        commentLabel.setBounds(50,250,100,30);
        String[] items = new String[DBConnection.family.getFamMember().size()];
        int i = 0;
        for (FamilyMember familyMember:  DBConnection.family.getFamMember()){
            items [i] = familyMember.getName();
            i++;
        }
        famBox = new JComboBox(items);
        famBox.setBounds(160,50,150,30);
        dateTextField.setBounds(160,100,150,30);
        amountTextField.setBounds(160, 200, 150, 30);
        commentTextField.setBounds(160, 250, 150, 30);

        container.add(amountLabel);
        container.add(commentLabel);
        container.add(famBox);
        container.add(dateTextField);
        container.add(amountTextField);
        container.add(commentTextField);
    }
    public void addUser(){
        setLayoutManager();
        setLocationAndSizeUser();
        action();
        addComponentsToContainerUser();
        setTitle("Adding a user");
        setVisible(true);
        setSize(380,350);
        setLocationRelativeTo(null);
        //   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    public void setLocationAndSizeUser()
    {
        userLabelTitle.setBounds(80, 15, 220, 30);
        userLabelTitle.setFont(fontTitle);
        nameLabel.setBounds(30,50,150,30);
        dateOfBirthLabel.setBounds(30,100,150,30);
        degreeOfKinshipLabel.setBounds(30,150,150,30);
        personalBudgetLabel.setBounds(30,200, 150, 30);
        nameTextField.setBounds(200, 50, 150, 30);
        dateOfBirthTextField.setBounds(200, 100, 150, 30);
        degreeOfKinshipTextField.setBounds(200, 150, 150, 30);
        personalBudgetTextField.setBounds(200, 200, 150, 30);
        addUserButton.setBounds(120,250,100,40);
    }
    public void addComponentsToContainerUser()
    {
        container.add(userLabelTitle);
        container.add(nameLabel);
        container.add(dateOfBirthLabel);
        container.add(degreeOfKinshipLabel);
        container.add(personalBudgetLabel);
        container.add(nameTextField);
        container.add(dateOfBirthTextField);
        container.add(degreeOfKinshipTextField);
        container.add(personalBudgetTextField);
        container.add(addUserButton);
    }
    public void income()
    {
        setLayoutManager();
        setLocationAndSizeIncome();
        setLocationAndAddEl();
        action();
        addComponentsToContainerIncome();
        setTitle("Adding revenue");
        setVisible(true);
        setSize(380,400);
        setLocationRelativeTo(null);
     //   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    public void setLocationAndSizeIncome()
    {
        incomeLabelTitle.setBounds(100, 15, 200, 30);
        incomeLabelTitle.setFont(fontTitle);
        famMemberLabel.setBounds(50,50,100,30);
        dateIncomeLabel.setBounds(50,100,100,30);
        incomeSourceLabel.setBounds(50,150,110,30);
        String[] items = new String[SourceOfIncome.getIncomeList().size()];
        int i = 0;
        for (String source: SourceOfIncome.getIncomeList()){
            items [i] = source;
            i++;
        }
        incomeBox = new JComboBox(items);
        incomeBox.setBounds(160, 150, 150, 30);
        addIncomeButton.setBounds(120,300,100,40);
    }
    public void addComponentsToContainerIncome()
    {
        container.add(incomeLabelTitle);
        container.add(famMemberLabel);
        container.add(dateIncomeLabel);
        container.add(incomeSourceLabel);
        container.add(incomeBox);
        container.add(addIncomeButton);
    }
    public void expenditure()
    {
        setLayoutManager();
        setLocationAndSizeExpenditure();
        setLocationAndAddEl();
        action();
        addComponentsToContainerExpenditure();
        setTitle("Adding an expense");
        setVisible(true);
        setSize(380,400);
        setLocationRelativeTo(null);
  //      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    public void setLocationAndSizeExpenditure()
    {
        expenditureLabelTitle.setBounds(100, 15, 200, 30);
        expenditureLabelTitle.setFont(fontTitle);
        famMemberLabel.setBounds(50,50,100,30);
        dateExpenditureLabel.setBounds(50,100,100,30);
        expenditureSourceLabel.setBounds(50,150,110,30);
        String[] items = new String[ItemOfExpenditure.getExpenditureList().size()];
        int i = 0;
        for (String item: ItemOfExpenditure.getExpenditureList()){
            items [i] = item;
            i++;
        }
        expenditureBox = new JComboBox(items);
        expenditureBox.setBounds(160, 150, 150, 30);
        addExpenditureButton.setBounds(120,300,100,40);
    }
    public void addComponentsToContainerExpenditure()
    {
        container.add(expenditureLabelTitle);
        container.add(famMemberLabel);
        container.add(dateExpenditureLabel);
        container.add(expenditureSourceLabel);
        container.add(expenditureBox);
        container.add(addExpenditureButton);
    }

    public void action(){
        addIncomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==addIncomeButton){
                    if (!dateTextField.getText().equals("") && !amountTextField.getText().equals("") && !commentTextField.getText().equals("")){
                        if (Check.checkDate(dateTextField.getText()) && Check.checkSum(Integer.parseInt(amountTextField.getText()))) {
                            AccountingForCurrentIncome income = new AccountingForCurrentIncome(famBox.getSelectedItem().toString(), dateTextField.getText(), incomeBox.getSelectedItem().toString(), Integer.parseInt(amountTextField.getText()), commentTextField.getText());
                            boolean add = DBConnection.addIncome(income);
                            if (add) {
                                setVisible(false);
                            }
                            DBMethods.updatePersonalBudget(1, income.getAmount(), income.getMember());
                        }
                        else JOptionPane.showMessageDialog(new JOptionPane(), "Не все поля соответствуют формату!");
                    }
                    else JOptionPane.showMessageDialog(new JOptionPane(), "Ошибка! Заполните поля ввода!");
                }
            }
        });
        addExpenditureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==addExpenditureButton){
                    if (!dateTextField.getText().equals("") && !amountTextField.getText().equals("") && !commentTextField.getText().equals("")){
                        if (Check.checkDate(dateTextField.getText()) && Check.checkSum(Integer.parseInt(amountTextField.getText()))) {
                            AccountingForCurrentExpenses expenditure = new AccountingForCurrentExpenses(famBox.getSelectedItem().toString(), dateTextField.getText(), expenditureBox.getSelectedItem().toString(), Integer.parseInt(amountTextField.getText()), commentTextField.getText());
                            boolean add = DBConnection.addExpenditure(expenditure);
                            if (add) {
                                setVisible(false);
                            }
                            DBMethods.updatePersonalBudget(2, expenditure.getAmount(), expenditure.getMember());
                        }
                        else JOptionPane.showMessageDialog(new JOptionPane(), "Не все поля соответствуют формату!");
                    }
                    else JOptionPane.showMessageDialog(new JOptionPane(), "Ошибка! Заполните поля ввода!");
                }
            }
        });
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==addUserButton) {
                    if (!nameTextField.getText().equals("") && !dateOfBirthTextField.getText().equals("") && !degreeOfKinshipTextField.getText().equals("") && !personalBudgetTextField.getText().equals("")) {
                        if (Check.checkDate(dateTextField.getText()) && Check.checkSum(Integer.parseInt(amountTextField.getText()))) {
                            setVisible(false);
                            FamilyMember familyMember = new FamilyMember(nameTextField.getText(), dateOfBirthTextField.getText(), degreeOfKinshipTextField.getText(), Integer.parseInt(personalBudgetTextField.getText()));
                            boolean add = DBConnection.addMember(familyMember);
                            if (add) {
                                setVisible(false);
                                new Thread(new MainFrame());
                            }
                        }
                        else JOptionPane.showMessageDialog(new JOptionPane(), "Не все поля соответствуют формату!");
                    } else
                        System.err.println("Ошибка считывания информации из полей! Пользователь не добавлен!");
                }
            }
        });
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
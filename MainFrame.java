package com.app.frame;

import com.app.server.DBConnection;
import com.db.connection.DBMethods;
import com.home.bookkeeping.ItemOfExpenditure;
import com.home.bookkeeping.SourceOfIncome;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class MainFrame extends JFrame implements ActionListener, Runnable {
    static private Socket socket;
    static private ObjectOutputStream output;
    static private ObjectInputStream input;
    Container container = getContentPane();
    //список
    final JLabel labelTitleList = new JLabel("Список членов семьи");
    final DefaultListModel model = new DefaultListModel();
    final JList listFamMembers = new JList(model);
    final JButton addFamMemberButton = new JButton("Добавить");
    final JButton deleteFamMemberButton = new JButton("Удалить");
    //информация о члене семьи
    final JLabel nameLabel = new JLabel("Имя");
    final JLabel dateOfBirthLabel = new JLabel("Дата рождения");
    final JLabel degreeOfKinshipLabel = new JLabel("Степень родства");
    final JLabel personalBudgetLabel = new JLabel("Личный бюджет");
    final JTextField nameTextField = new JTextField();
    final JTextField dateOfBirthTextField = new JTextField();
    final JTextField degreeOfKinshipTextField = new JTextField();
    final JTextField personalBudgetTextField = new JTextField();
    //информация о семьи
    final JButton fullFamGetButton = new JButton("Вся семья");
    final JButton incomeButton = new JButton("Доход");
    final JButton expenditureButton = new JButton("Расход");
    final Color buttonColor = new Color(70, 130, 180);
    final Color buttonTextColor = new Color(245, 255, 250);
    public MainFrame()
    {
        setLayoutManager();
        setLocationAndSize();
        action();
        addComponentsToContainer();
        setTitle("Home Bookkeeping");
        setVisible(true);
        setSize(450,400);
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
        Font font = new Font("serif", Font.PLAIN, 10);
        labelTitleList.setBounds(5, 5, 150, 30);
        model.clear();
        for(var fm:DBConnection.family.getFamMember()) {
            model.addElement(fm.getName());
        }
        Font f = new Font("Courier", Font.ITALIC + Font.BOLD, 14);
        listFamMembers.setFont(f);
        listFamMembers.setBounds(0, 40, 120, 220);
        listFamMembers.setBackground(new Color(245, 245, 245));
        addFamMemberButton.setBounds(10, 280, 100, 30);
        addFamMemberButton.setBackground(buttonColor);
        addFamMemberButton.setForeground(buttonTextColor);
        deleteFamMemberButton.setBounds(10, 320, 100, 30);
        deleteFamMemberButton.setBackground(buttonColor);
        deleteFamMemberButton.setForeground(buttonTextColor);
        nameLabel.setBounds(180, 58, 100, 15);
        dateOfBirthLabel.setBounds(180, 108, 100,  15);
        degreeOfKinshipLabel.setBounds(180, 158, 100,  15);
        personalBudgetLabel.setBounds(180, 208, 100,  15);
        nameTextField.setBounds(300, 50, 100, 30);
        dateOfBirthTextField.setBounds(300, 100, 100, 30);
        degreeOfKinshipTextField.setBounds(300, 150, 100, 30);
        personalBudgetTextField.setBounds(300, 200, 100, 30);
        fullFamGetButton.setBounds(250, 5, 100, 20);
        fullFamGetButton.setBackground(buttonColor);
        fullFamGetButton.setForeground(buttonTextColor);
        expenditureButton.setBounds(200, 300, 80, 30);
        expenditureButton.setBackground(buttonColor);
        expenditureButton.setForeground(buttonTextColor);
        incomeButton.setBounds(300, 300, 80, 30);
        incomeButton.setBackground(buttonColor);
        incomeButton.setForeground(buttonTextColor);
    }
    public void updateInfo(){
        setVisible(false);
        new MainFrame();
    }
    public void action(){
        listFamMembers.addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        Object element = listFamMembers.getSelectedValue();
                        int index = listFamMembers.getSelectedIndex();
                        nameTextField.setText(element.toString());
                        dateOfBirthTextField.setText(DBConnection.family.getFamMember().get(index).getDateOfBirth());
                        degreeOfKinshipTextField.setText(DBConnection.family.getFamMember().get(index).getDegreeOfKinship());
                        personalBudgetTextField.setText(String.valueOf(DBConnection.family.getFamMember().get(index).getPersonalBudget()));
                    }
                });
        addFamMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==addFamMemberButton){
                    new Thread(new FuncFrame(3));
                    setVisible(false);
                }
            }
        });
        deleteFamMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==deleteFamMemberButton){
                    if(!listFamMembers.isSelectionEmpty()) {
                        boolean del = DBConnection.deleteMember(listFamMembers.getSelectedValue().toString());
                        if (del) {
                            updateInfo();
                        }
                    }
                    else JOptionPane.showMessageDialog(null, "Пользователь не выбран!");
                }
            }
        });
        incomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==incomeButton){
                    if (SourceOfIncome.getIncomeList().size() == 0)
                        DBMethods.setIncomeList();
                    new Thread(new FuncFrame(1)).start();
                }
            }
        });
        expenditureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==expenditureButton) {
                    if (ItemOfExpenditure.getExpenditureList().size() == 0)
                        DBMethods.setExpensesList();
                    new Thread(new FuncFrame(2)).start();
                }
            }
        });
        fullFamGetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==fullFamGetButton) {
                    DBMethods.calcFamBudget();
                    DBMethods.updateFamBudget();
                    if (DBConnection.family.getIncomeList().isEmpty())
                        DBMethods.setAccountingIncomeList();
                    if (DBConnection.family.getExpensesList().size() == 0)
                        DBMethods.setAccountingExpenditureList();
                    new Thread(new FullFamFrame()).start();
                }
            }
        });
    }

    public void addComponentsToContainer()
    {
        container.add(labelTitleList);
        container.add(listFamMembers);
        container.add(addFamMemberButton);
        container.add(deleteFamMemberButton);
        container.add(nameLabel);
        container.add(dateOfBirthLabel);
        container.add(degreeOfKinshipLabel);
        container.add(personalBudgetLabel);
        container.add(nameTextField);
        container.add(dateOfBirthTextField);
        container.add(degreeOfKinshipTextField);
        container.add(personalBudgetTextField);
        container.add(fullFamGetButton);
        container.add(expenditureButton);
        container.add(incomeButton);
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

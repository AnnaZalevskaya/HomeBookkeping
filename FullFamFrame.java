package com.app.frame;

import com.app.server.DBConnection;
import com.home.bookkeeping.AccountingForCurrentIncome;
import com.home.bookkeeping.Family;

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
import java.util.Vector;

public class FullFamFrame extends JFrame implements Runnable {
    static private Socket socket;
    static private ObjectOutputStream output;
    static private ObjectInputStream input;
    Container container = getContentPane();
    final JLabel famLabelTitle = new JLabel("Учет бюджета семьи");
    final JLabel budgetLabel = new JLabel("Общий бюджет");
    final JLabel getBudgetLabel = new JLabel(String.valueOf(DBConnection.family.getBudget()));
    final JLabel incomeLabel = new JLabel("Учет доходов:");
    final JLabel expenditureLabel = new JLabel("Учет расходов:");
    JTable accountingIncome;
    JTable accountingExpenditure;
    final JButton sortUpIncome = new JButton("^");
    final JButton sortDownIncome = new JButton("v");
    final JLabel poiskIncomeLabel = new JLabel("Поиск по доходам:");
    final JTextField poiskIncome = new JTextField();
    final JButton poiskIncomeButton = new JButton("Поиск");
    final JButton sortUpExpenditure = new JButton("^");
    final JButton sortDownExpenditure = new JButton("v");
    final JLabel poiskExpenditureLabel = new JLabel("Поиск по расходам:");
    final JTextField poiskExpenditure = new JTextField();
    final JButton poiskExpensesButton = new JButton("Поиск");
    final Font sortFont = new Font("Arial", Font.PLAIN+Font.BOLD, 10);
    final Color buttonColor = new Color(70, 130, 180);
    final Color buttonTextColor = new Color(245, 255, 250);
    JScrollPane scrollIncome;
    JScrollPane scrollExpenditure;
    public void setLayoutManager()
    {
        container.setLayout(null);
    }
    public FullFamFrame()
    {
        setLayoutManager();
        setLocationAndSizeFam();
        action();
        addComponentsToContainerFam();
        setTitle("Home Bookkeeping");
        setVisible(true);
        setSize(700,700);
        setLocationRelativeTo(null);
        //      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    public void setLocationAndSizeFam(){
        Font fontTitle = new Font("serif", Font.PLAIN, 24);
        Font font1 = new Font("Arial", Font.BOLD + Font.ITALIC, 16);
        Font fontLabel = new Font("Arial", Font.BOLD, 15);
        famLabelTitle.setBounds(230, 10, 250, 30);
        famLabelTitle.setFont(fontTitle);
        budgetLabel.setBounds(15, 50, 200, 30);
        budgetLabel.setFont(font1);
        getBudgetLabel.setBounds(220, 50, 100, 30);
        getBudgetLabel.setFont(font1);
        expenditureLabel.setBounds(20, 370, 120, 30);
        expenditureLabel.setFont(fontLabel);
        incomeLabel.setBounds(20, 120, 120, 30);
        incomeLabel.setFont(fontLabel);
        //заполнение таблиц
        Vector column_names_i = new Vector();
        column_names_i.add("Член семьи");
        column_names_i.add("Дата дохода");
        column_names_i.add("Источник дохода");
        column_names_i.add("Сумма");
        column_names_i.add("Комментарий");
        Vector vec_data_i = new Vector();
        for(var el: DBConnection.family.getIncomeList()) {
            Vector el_info = new Vector();
            el_info.add(el.getMember());
            el_info.add(el.getDate());
            el_info.add(el.getSource());
            el_info.add(el.getAmount());
            el_info.add(el.getComment());
            vec_data_i.add(el_info);
        }
        accountingIncome = new JTable(vec_data_i, column_names_i);
        accountingIncome.setBounds(20, 150, 500, 200);
        accountingIncome.setBackground(new Color(245, 245, 245));
        scrollIncome = new JScrollPane(accountingIncome);
        scrollIncome.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollIncome.setBounds(20, 150, 500, 200);
        sortUpIncome.setBounds(560, 165, 30, 30);
        sortUpIncome.setFont(sortFont);
        sortUpIncome.setBackground(buttonColor);
        sortUpIncome.setForeground(buttonTextColor);
        sortUpIncome.setMargin(new Insets(0, 0, 0,0));
        sortDownIncome.setBounds(600, 165, 30, 30);
        sortDownIncome.setFont(sortFont);
        sortDownIncome.setBackground(buttonColor);
        sortDownIncome.setForeground(buttonTextColor);
        sortDownIncome.setMargin(new Insets(0, 0, 0,0));
        poiskIncomeLabel.setBounds(540, 210, 120, 20);
        poiskIncome.setBounds(550, 230, 100, 20);
        poiskIncomeButton.setBounds(550, 255, 100, 20);
        poiskIncomeButton.setBackground(buttonColor);
        poiskIncomeButton.setForeground(buttonTextColor);
        Vector column_names_e = new Vector();
        column_names_e.add("Член семьи");
        column_names_e.add("Дата расхода");
        column_names_e.add("Статья расхода");
        column_names_e.add("Сумма");
        column_names_e.add("Комментарий");
        Vector vec_data_e = new Vector();
        for (var el : DBConnection.family.getExpensesList()) {
            Vector el_info = new Vector();
            el_info.add(el.getMember());
            el_info.add(el.getDate());
            el_info.add(el.getExpenditure());
            el_info.add(el.getAmount());
            el_info.add(el.getComment());
            vec_data_e.add(el_info);
        }
        accountingExpenditure = new JTable(vec_data_e, column_names_e);
        accountingExpenditure.setBounds(20, 400, 500, 200);
        accountingExpenditure.setBackground(new Color(245, 245, 245));
        scrollExpenditure = new JScrollPane(accountingExpenditure);
        scrollExpenditure.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollExpenditure.setBounds(20, 400, 500, 200);
        sortUpExpenditure.setBounds(560, 415, 30, 30);
        sortUpExpenditure.setFont(sortFont);
        sortUpExpenditure.setBackground(buttonColor);
        sortUpExpenditure.setForeground(buttonTextColor);
        sortUpExpenditure.setMargin(new Insets(0, 0, 0,0));
        sortDownExpenditure.setBounds(600, 415, 30, 30);
        sortDownExpenditure.setFont(sortFont);
        sortDownExpenditure.setBackground(buttonColor);
        sortDownExpenditure.setForeground(buttonTextColor);
        sortDownExpenditure.setMargin(new Insets(0, 0, 0,0));
        poiskExpenditureLabel.setBounds(540, 460, 120, 20);
        poiskExpenditure.setBounds(550, 480, 100, 20);
        poiskExpensesButton.setBounds(550, 505, 100, 20);
        poiskExpensesButton.setBackground(buttonColor);
        poiskExpensesButton.setForeground(buttonTextColor);
    }
    public void addComponentsToContainerFam(){
        container.add(famLabelTitle);
        container.add(budgetLabel);
        container.add(getBudgetLabel);
        container.add(incomeLabel);
        container.add(expenditureLabel);
   //     container.add(accountingIncome);
        container.add(scrollIncome);
   //     container.add(accountingExpenditure);
        container.add(scrollExpenditure);
        container.add(sortUpIncome);
        container.add(sortDownIncome);
        container.add(sortUpExpenditure);
        container.add(sortDownExpenditure);
        container.add(poiskIncome);
        container.add(poiskIncomeLabel);
        container.add(poiskExpenditure);
        container.add(poiskExpenditureLabel);
        container.add(poiskIncomeButton);
        container.add(poiskExpensesButton);
    }
    public void updateInfo(){
        setVisible(false);
        new FullFamFrame();
    }
    public void action(){
        poiskIncomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==poiskIncomeButton) {
                    if (!poiskIncome.getText().equals("")) {
                        ListSelectionModel model = accountingIncome.getSelectionModel();
                        model.clearSelection();
                        for (int poisk: Family.poiskIncomeInList(poiskIncome.getText())) {
                            model.addSelectionInterval(poisk, poisk);
                        }
                    }
                    else JOptionPane.showMessageDialog(null, "Заполните текстовое поле для поиска!");
                }
            }
        });
        poiskExpensesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==poiskExpensesButton) {
                    if (!poiskExpenditure.getText().equals("")) {
                        ListSelectionModel model1 = accountingExpenditure.getSelectionModel();
                        model1.clearSelection();
                        for (int poisk: Family.poiskExpensesInList(poiskExpenditure.getText())) {
                            model1.addSelectionInterval(poisk, poisk);
                        }
                    }
                    else JOptionPane.showMessageDialog(null, "Заполните текстовое поле для поиска!");
                }
            }
        });
        sortUpIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==sortUpIncome) {
                    if (accountingIncome.getSelectedColumn() > -1) {
                        Family.sortListIncome(accountingIncome.getSelectedColumn(), 0, DBConnection.family.getIncomeList());
                        updateInfo();
                    }
                    else JOptionPane.showMessageDialog(null, "Выберите столбец для сортировки!");
                }
            }
        });
        sortDownIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==sortDownIncome) {
                    if (accountingIncome.getSelectedColumn() > -1) {
                        Family.sortListIncome(accountingIncome.getSelectedColumn(), 1, DBConnection.family.getIncomeList());
                        updateInfo();
                    }
                    else JOptionPane.showMessageDialog(null, "Выберите столбец для сортировки!");
                }
            }
        });
        sortUpExpenditure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==sortUpExpenditure) {
                    if (accountingExpenditure.getSelectedColumn() > -1) {
                        Family.sortListExpenses(accountingExpenditure.getSelectedColumn(), 0, DBConnection.family.getExpensesList());
                        updateInfo();
                    }
                    else JOptionPane.showMessageDialog(null, "Выберите столбец для сортировки!");
                }
            }
        });
        sortDownExpenditure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==sortDownExpenditure) {
                    if (accountingExpenditure.getSelectedColumn() > -1) {
                        Family.sortListExpenses(accountingExpenditure.getSelectedColumn(), 1, DBConnection.family.getExpensesList());
                        updateInfo();
                    }
                    else JOptionPane.showMessageDialog(null, "Выберите столбец для сортировки!");
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

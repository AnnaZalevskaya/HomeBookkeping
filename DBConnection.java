package com.app.server;

import com.app.client.Client;
import com.app.frame.LoginFrame;
import com.app.frame.MainFrame;
import com.db.connection.*;
import com.home.bookkeeping.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection extends Server implements Runnable{
    static public Family family = null;
    public static boolean userAuthorization(String surname, String password) {
        boolean auth = false;
        DB.connectionDB();
        try {
            ResultSet rs = DB.statement.executeQuery("select id from family where surname = '" + surname + "' && password = '" + password + "'");
            if(rs.next()){
                family = new Family(rs.getInt("id"), surname, password);
                DBMethods.setFamList();
                output.writeObject("Авторизация прошла успешно!");
                output.flush();
                System.out.println("Авторизация прошла успешно!");
                auth = true;
                LoginFrame.mainForm = new Thread(new MainFrame());
                LoginFrame.mainForm.start();
            }
            else{
                output.writeObject("Такого пользователя не существует!");
                output.flush();
                System.err.println("Такого пользователя не существует!");
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        DB.closeConnection();
        return auth;
    }

    public static boolean userRegistration(String surname, String password) {
        boolean register = false;
        DB.connectionDB();
        try {
            ResultSet rs = DB.statement.executeQuery("select * from family where surname = '" + surname + "' && password = '" + password + "'");
            if (!rs.next()){
                DB.statement.executeUpdate("insert into family (surname, password) values ('" + surname + "', '" + password + "')");
                output.writeObject("Регистрация прошла успешно!");
                output.flush();
                register = true;
            }
            else {
                output.writeObject("Такой пользователь уже существует");
                output.flush();
                System.err.println("Такой пользователь уже существует");
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        DB.closeConnection();
        return register;
    }
    public static boolean addMember(FamilyMember fm) {
        boolean add = false;
        DB.connectionDB();
        try {
            int rs = DB.statement.executeUpdate("insert into family_member (family_id, name, date_of_birth, degree_of_kinship, personal_budget) values ("
                    + family.getId() + ", '" + fm.getName() + "', '" + fm.getSqlDate() + "', '" + fm.getDegreeOfKinship() + "', '" + fm.getPersonalBudget() + "')");
            if (rs > 0) {
                family.setFamMember(fm);
                add = true;
                output.writeObject("Новый член семьи успешно добавлен!");
                output.flush();
            }
            else {
                output.writeObject("Новый член семьи не добавлен! Ошибка добавление в БД!");
                output.flush();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        DB.closeConnection();
        return add;
    }
    public static boolean deleteMember(String name){
        boolean del = false;
        DB.connectionDB();
        try {
            int i = DB.statement.executeUpdate("delete from family_member where name = '" + name + "'");
            if (i > 0) {
                output.writeObject("Член семьи успешно удален!");
                output.flush();
                for (var fm : family.getFamMember()) {
                    if (name.equals(fm.getName())) {
                        family.getFamMember().remove(fm);
                        System.out.println("Член семьи удален!");
                        del = true;
                        output.writeObject("Член семьи удален!");
                        output.flush();
                        break;
                    }
                }
                if (!del) System.err.println("Такого члена семьи нет!");
            }
            else {
                output.writeObject("Ошибка! Член семьи не был удален!");
                output.flush();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        DB.closeConnection();
        return del;
    }
    public static boolean addIncome(AccountingForCurrentIncome income){
        boolean add = false;
        DB.connectionDB();
        try {
            int rs = DB.statement.executeUpdate("insert into accounting_for_current_income (family_id, family_member_id, date, source_of_income_id, amount, comment)  values ("
                   + family.getId() + ", "+ DBMethods.getMember(income.getMember()) +  ", '" + income.getSqlDate() + "', " + DBMethods.getIncome(income.getSource()) + ", " + income.getAmount() + ", '" + income.getComment() +"')");
            if (rs > 0) {
                add = true;
                family.addIncomeList(income);
                output.writeObject("Новый доход успешно добавлен!");
                output.flush();
            }
            else {
                output.writeObject("Ошибка! Доход не добавлен!");
                output.flush();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        DB.closeConnection();
        return add;
    }
    public static boolean addExpenditure(AccountingForCurrentExpenses expenditure){
        boolean add = false;
        DB.connectionDB();
        try {
            int rs = DB.statement.executeUpdate("insert into accounting_for_current_expenses (family_id, family_member_id, date, item_of_expenditure_id, amount, comment) values ("
                    + family.getId() + ", "+ DBMethods.getMember(expenditure.getMember()) +  ", '" + expenditure.getSqlDate() + "', " + DBMethods.getExpenditure(expenditure.getExpenditure()) + ", " + expenditure.getAmount() + ", '" + expenditure.getComment() +"')");
            if (rs > 0) {
                add = true;
                family.addExpensesList(expenditure);
                output.writeObject("Новый расход успешно добавлен!");
                output.flush();
            }
            else {
                output.writeObject("Ошибка! Расход не добавлен!");
                output.flush();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        DB.closeConnection();
        return add;
    }
    public static void addSourceIncome(String name){
        DB.connectionDB();
        try {
            int rs = DB.statement.executeUpdate("insert into source_of_income (name_of_income) values ('" + name + "')");
            if (rs > 0) {
                SourceOfIncome.setIncomeList(name);
                output.writeObject("Новый возможный источник дохода успешно добавлен!");
                output.flush();
            }
            else {
                output.writeObject("Ошибка! Источник дохода не добавлен!");
                output.flush();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        DB.closeConnection();
    }
    public static void deleteSourceIncome(String name){
        int id = SourceOfIncome.poiskIndex(name);
        DB.connectionDB();
        try {
            int rs = DB.statement.executeUpdate("delete from source_of_income where name_of_income = '" + name + "'");
            if (rs > 0) {
                SourceOfIncome.getIncomeList().remove(id);
                output.writeObject("Выбранный возможный источник дохода успешно удален!");
                output.flush();
            }
            else {
                output.writeObject("Ошибка! Выбранный источник дохода не удален!");
                output.flush();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        DB.closeConnection();
    }
    public static void editSourceIncome(String new_name, String name){
        int id = SourceOfIncome.poiskIndex(name);
        DB.connectionDB();
        try {
            int rs = DB.statement.executeUpdate("update source_of_income set name_of_income = '" + new_name + "' where name_of_income = '" + name + "'");
            if (rs > 0) {
                SourceOfIncome.getIncomeList().remove(id);
                SourceOfIncome.getIncomeList().add(id, new_name);
                output.writeObject("Выбранный возможный источник дохода успешно изменен!");
                output.flush();
            }
            else {
                output.writeObject("Ошибка! Выбранный источник дохода не изменен!");
                output.flush();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        DB.closeConnection();
    }
    public static void addItemExpenditure(String name){
        DB.connectionDB();
        try {
            int rs = DB.statement.executeUpdate("insert into item_of_expenditure (name_of_expenditure) values ('" + name + "')");
            if (rs > 0) {
                ItemOfExpenditure.setExpenditureList(name);
                output.writeObject("Новая возможная статья расхода успешно добавлена!");
                output.flush();
            }
            else {
                output.writeObject("Ошибка! Статья расхода не добавлена!");
                output.flush();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        DB.closeConnection();
    }
    public static void deleteItemExpenditure(String name){
        int id = ItemOfExpenditure.poiskIndex(name);
        DB.connectionDB();
        try {
            int rs = DB.statement.executeUpdate("delete from item_of_expenditure where name_of_expenditure = '" + name + "'");
            if (rs > 0) {
                ItemOfExpenditure.getExpenditureList().remove(id);
                output.writeObject("Выбранная возможная статья расхода успешно удалена!");
                output.flush();
            }
            else {
                output.writeObject("Ошибка! Выбранная статья расхода не удалена!");
                output.flush();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        DB.closeConnection();
    }
    public static void editItemExpenditure(String new_name, String name){
        int id = ItemOfExpenditure.poiskIndex(name);
        DB.connectionDB();
        try {
            int rs = DB.statement.executeUpdate("update item_of_expenditure set name_of_expenditure = '" + new_name +"' where name_of_expenditure = '" + name + "'");
            if (rs > 0) {
                ItemOfExpenditure.getExpenditureList().remove(id);
                ItemOfExpenditure.getExpenditureList().add(id, new_name);
                output.writeObject("Выбранная возможная статья расхода успешно изменена!");
                output.flush();
            }
            else {
                output.writeObject("Ошибка! Выбранная статья расхода не изменена!");
                output.flush();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        DB.closeConnection();
    }
}
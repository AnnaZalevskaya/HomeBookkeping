package com.home.bookkeeping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Family {
    private int id;
    private String surname;
    private List<FamilyMember> famList = new ArrayList<>();
    private String password;
    private float budget;
    public Family(int id, String surname, String password){
        this.id = id;
        this.surname = surname;
        this.password = password;
    }
    public int getId() { return id;}
    public String getSurname() {
        return surname;
    }
    public List<FamilyMember> getFamMember() {
        return famList;
    }
    public int indexFamMember(String name) {
        int i = -1;
        for (int k = 0; k < famList.size(); k++) {
            if (famList.get(k).getName() == name) {
                i = k;
                break;
            }
        }
        return i;
    }
    public void setFamMember(FamilyMember famMember) {
        famList.add(famMember);
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public float getBudget() {
        return budget;
    }
    public void setBudget(float budget) {
        this.budget = budget;
    }
    private static List<AccountingForCurrentIncome> accountingForCurrentIncomeList = new ArrayList<>();
    private static List<AccountingForCurrentExpenses> accountingForCurrentExpensesList = new ArrayList<>();

    public List<AccountingForCurrentIncome> getIncomeList() {
        return accountingForCurrentIncomeList;
    }
    public void addIncomeList (AccountingForCurrentIncome income) {
        accountingForCurrentIncomeList.add(income);
    }
    public List<AccountingForCurrentExpenses> getExpensesList() {
        return accountingForCurrentExpensesList;
    }
    public void addExpensesList (AccountingForCurrentExpenses expenses) {
        accountingForCurrentExpensesList.add(expenses);
    }
    public static void sortListIncome(int i_, int j_, List<AccountingForCurrentIncome> list) {
        AccountingForCurrentIncome.appField i = AccountingForCurrentIncome.appField.values()[i_];
        AccountingForCurrentIncome.sortingDirection j = AccountingForCurrentIncome.sortingDirection.values()[j_];
        System.out.println(i + " " + j);
        Collections.sort(list, new Comparator<AccountingForCurrentIncome>() {
            @Override
            public int compare(AccountingForCurrentIncome o1, AccountingForCurrentIncome o2) {
                if (j.name() == "INCREASING") {
                    if (i.field == 4) {
                        return Float.compare(Float.valueOf(o1.getMethFunc(i)), Float.valueOf(o2.getMethFunc(i)));
                    } else {
                        return o1.getMethFunc(i).compareTo(o2.getMethFunc(i));
                    }
                }
                else if (j.name() == "DECREASING"){
                    if (i.field == 4) {
                        return Float.compare(Float.valueOf(o2.getMethFunc(i)), Float.valueOf(o1.getMethFunc(i)));
                    }
                    return o2.getMethFunc(i).compareTo(o1.getMethFunc(i));
                }
                return 0;
            }
        });
    }
    public static void sortListExpenses(int i_, int j_, List<AccountingForCurrentExpenses> list) {
        AccountingForCurrentExpenses.appField i = AccountingForCurrentExpenses.appField.values()[i_];
        AccountingForCurrentExpenses.sortingDirection j = AccountingForCurrentExpenses.sortingDirection.values()[j_];
        Collections.sort(list, new Comparator<AccountingForCurrentExpenses>() {
            @Override
            public int compare(AccountingForCurrentExpenses o1, AccountingForCurrentExpenses o2) {
                if (j.name() == "INCREASING") {
                    if (i.field == 4) {
                        return Float.compare(Float.valueOf(o1.getMethFunc(i)), Float.valueOf(o2.getMethFunc(i)));
                    } else {
                        return o1.getMethFunc(i).compareTo(o2.getMethFunc(i));
                    }
                }
                else if (j.name() == "DECREASING") {
                    if (i.field == 4) {
                        return Float.compare(Float.valueOf(o2.getMethFunc(i)), Float.valueOf(o1.getMethFunc(i)));
                    }
                    return o2.getMethFunc(i).compareTo(o1.getMethFunc(i));
                }
                return 0;
            }
        });
    }
    public static List<Integer> poiskIncomeInList(String word) {
        List<Integer> index = new ArrayList<>();
        for (int inc = 0; inc < accountingForCurrentIncomeList.size(); inc++) {
            if (accountingForCurrentIncomeList.get(inc).getMember().equals(word) || accountingForCurrentIncomeList.get(inc).getDate().equals(word) || accountingForCurrentIncomeList.get(inc).getSource().equals(word) || String.valueOf(accountingForCurrentIncomeList.get(inc).getAmount()).equals(word) || accountingForCurrentIncomeList.get(inc).getComment().equals(word)) {
                index.add(inc);
            }
        }
        return index;
    }
    public static List<Integer> poiskExpensesInList(String word) {
        List<Integer> ind = new ArrayList<>();
        for (int item = 0; item < accountingForCurrentExpensesList.size(); item++) {
            if (accountingForCurrentExpensesList.get(item).getMember().equals(word) || accountingForCurrentExpensesList.get(item).getDate().equals(word) || accountingForCurrentExpensesList.get(item).getExpenditure().equals(word) || String.valueOf(accountingForCurrentExpensesList.get(item).getAmount()).equals(word) || accountingForCurrentExpensesList.get(item).getComment().equals(word)) {
                ind.add(item);
            }
        }
        return ind;
    }
}

package com.home.bookkeeping;

import java.util.ArrayList;
import java.util.List;

public class SourceOfIncome {
    private static List<String> incomeList = new ArrayList<>();
    public static List<String> getIncomeList(){
        return incomeList;
    }
    public static void setIncomeList(String source){
        incomeList.add(source);
    }
    public static int poiskIndex (String name){
        int i = -1;
        for (int k = 0; k < incomeList.size(); k++) {
            if (incomeList.get(k) == name) {
                i = k;
                break;
            }
        }
        return i;
    }
}

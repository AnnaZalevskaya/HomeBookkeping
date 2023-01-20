package com.home.bookkeeping;

import java.util.ArrayList;
import java.util.List;

public class ItemOfExpenditure {
    private static List<String> expenditureList = new ArrayList<>();
    public static List<String> getExpenditureList(){
        return expenditureList;
    }
    public static void setExpenditureList(String source){
        expenditureList.add(source);
    }
    public static int poiskIndex (String name){
        int i = -1;
        for (int k = 0; k < expenditureList.size(); k++) {
            if (expenditureList.get(k) == name) {
                i = k;
                break;
            }
        }
        return i;
    }
}
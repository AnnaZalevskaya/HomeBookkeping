package com.home.bookkeeping;

public class Check {
    public static boolean checkDate(String date){
        String dateReg = "(0?[1-9]|[12][0-9]|3[01])(.)(0?[1-9]|1[012])(.)(20\\d\\d)";
        return date.matches(dateReg);
    }
    public static boolean checkSum(int ch) {
        if (ch > 0)
            return true;
        else return false;
    }
}

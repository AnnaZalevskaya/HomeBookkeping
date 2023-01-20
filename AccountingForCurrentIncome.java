package com.home.bookkeeping;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AccountingForCurrentIncome {
    private String member;
    private String date;
    private String source;
    private float amount;
    private String comment;
    public enum appField{
        MEMBER(1), DATE(2), INCOME(3), AMOUNT(4), COMMENT(5);
        public final int field;
        appField(int field) {
            this.field = field;
        }
    }
    public String getMethFunc(appField af){
        if (af.field == 1)
            return getMember();
        else if (af.field == 2)
            return getSqlDate();
        else if (af.field == 3)
            return getSource();
        else if (af.field == 4)
            return String.valueOf(getAmount());
        else if (af.field == 5)
            return getComment();
        return "";
    }
    public enum sortingDirection{
        INCREASING, DECREASING
    }
    public AccountingForCurrentIncome(String member, Date date, String source, float amount, String comment){
        setMember(member);
        transformDate(date);
        setSource(source);
        setAmount(amount);
        setComment(comment);
    }
    public AccountingForCurrentIncome(String member, String date, String source, float amount, String comment){
        setMember(member);
        setDate(date);
        setSource(source);
        setAmount(amount);
        setComment(comment);
    }
    public String getSqlDate(){
        String dates[] = this.date.split("\\.");
        String SqlDate = dates[2] + "-" + dates[1] + "-" + dates[0];
        return SqlDate;
    }
    public String getMember() {
        return member;
    }
    public void setMember(String member) {
        this.member = member;
    }
    public void transformDate(Date date){
        DateFormat dateFormat_ = new SimpleDateFormat("dd.MM.yyyy");
        this.date = dateFormat_.format(date);
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}


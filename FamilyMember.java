package com.home.bookkeeping;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class FamilyMember {
    private String name;
    private String dateOfBirth;
    private String degreeOfKinship;
    private float personalBudget;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDateOfBirth() {
        DateFormat dateFormat_ = new SimpleDateFormat("dd.MM.yyyy");
        return dateOfBirth;
    }
    public String getSqlDate(){
        String dates[] = this.dateOfBirth.split("\\.");
        String SqlDate = dates[2] + "-" + dates[1] + "-" + dates[0];
        return SqlDate;
    }
    public void setDateOfBirthStr(String date) {
        this.dateOfBirth = date;
    }
    public void transformDate(Date date){
        DateFormat dateFormat_ = new SimpleDateFormat("dd.MM.yyyy");
        this.dateOfBirth = dateFormat_.format(date);
    }
    public String getDegreeOfKinship() {
        return degreeOfKinship;
    }
    public void setDegreeOfKinship(String degreeOfKinship) {
        this.degreeOfKinship = degreeOfKinship;
    }
    public float getPersonalBudget() {
        return personalBudget;
    }
    public void setPersonalBudget(float personalBudget) {
        this.personalBudget = personalBudget;
    }
    public FamilyMember(String name, Date dateOfBirth, String degreeOfKinship, float personalBudget){
        setName(name);
        transformDate(dateOfBirth);
        setDegreeOfKinship(degreeOfKinship);
        setPersonalBudget(personalBudget);
    }
    public FamilyMember(String name, String dateOfBirth, String degreeOfKinship, float personalBudget){
        setName(name);
        setDateOfBirthStr(dateOfBirth);
        setDegreeOfKinship(degreeOfKinship);
        setPersonalBudget(personalBudget);
    }
}
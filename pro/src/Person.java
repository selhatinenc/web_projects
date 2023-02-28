package com.example.demo;

import javafx.beans.property.SimpleStringProperty;

public class Person {
 private SimpleStringProperty username,clubmember,admin,fourth;
    public Person(String username,String clubmember,String admin,String fourth){
        this.username=new SimpleStringProperty(username);this.clubmember=new SimpleStringProperty(clubmember);this.admin=new SimpleStringProperty(admin);;this.fourth=new SimpleStringProperty(fourth);;

    }
    public void setAdmin1( ){
        if (admin.get().toString().equals("true"))admin.setValue("false");
        else  if (admin.get().equals("false"))admin=new SimpleStringProperty("true");
    }  public void setClubmember1( ){
        if (clubmember.get().equals("true"))clubmember=new SimpleStringProperty("false");
        else  if (clubmember.get().equals("false"))clubmember=new SimpleStringProperty("true");
    }

    public String getUsername() {
        return username.get();
    }

    public String getClubmember() {
        return clubmember.get();
    }
    public String getFourth() {
        return fourth.get();
    }
    public String getAdmin() {
        return admin.get();
    }
    public void setAdmin(String s){
        setAdmin1();
    }
    public void setClubmember(String s2){
        setClubmember1();
    }
}

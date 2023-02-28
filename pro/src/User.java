package com.example.demo;

public class User{
    private String name,pasword,prompter,admin;
    public User(String name,String pasword1,String prompter1,String admin1){
        this.name=name;pasword=pasword1;prompter=prompter1;admin=admin1; ;

    }


    public void setPrompter(String p) {
        if (prompter.equals("true")) prompter="false";
        else prompter="true";
    }

    public void setAdmin(String a) {
        if (admin.equals("true")) admin="false";
        else admin="true";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public String getName() {
        return name;
    }

    public String getPasword() {
        return pasword;
    }

    public String getPrompter() {
        return prompter;
    }

    public String getAdmin() {
        return admin;
    }


}

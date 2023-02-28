package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class Users {
    private ObservableList<Person> personObservableList;
   private MenuButton menuButton;
 private  ArrayList<User> user;

    public Users() {
        user=new ArrayList<>();personObservableList= FXCollections.observableArrayList();
        menuButton=new MenuButton();

    }
    public MenuButton setMenuButton(Hall hall){
    menuButton.getItems().clear();
            for (User u:user){

            MenuItem menuItem= new MenuItem(u.getName());
            menuItem.setOnAction(event -> {menuButton.setText(menuItem.getText());
           hall.setOwner(menuButton.getText(),"true");
            }); menuButton.getItems().add(menuItem); }
        return menuButton;
    }
    public MenuButton getMenuButton(){
        return menuButton;
    }

    public void setUser(User user) {
        this.user.add(user);
    }
    public ArrayList<User> getUser(){
        return user;
    }

    public  boolean Isexist(String Uname){
        for (User u:user){
            if (u.getName().equals(Uname)) return true;
        }
        return false;
    }
    public  boolean IsAdmin(String Uname){
        for (User u:user){
            if (u.getName().equals(Uname)) return u.getAdmin().equals("true");
        }
        return false;
    }
    public boolean IsClubMember(String us){ for (User u:user){
        if (u.getName().equals(us)) return u.getPrompter().equals("true");
    }
        return false;}
    public void setPerson(){
personObservableList.clear();
            for (User u:user)
            {Person person=new Person(u.getName(),u.getPrompter(),u.getAdmin(),"");

            personObservableList.add(person);
    }
   }
    public ObservableList<Person> getPersonObservableList(){
        return  personObservableList;
    }
public  boolean Passwordmatch(String username,String password){
    for (User u:user){
        if (u.getName().equals(username)) if (u.getPasword().equals(password)) return  true; else return false;
    }
    return false;
}
}

package com.example.demo;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class Movies {  private ArrayList<Hall> halls;
    private String name;
  private   String path;private int munite;
  private MenuButton menuButton;
    public Movies(String mv,String path,int munite){
        this.path=path;this.munite=munite;
        name=mv;
        halls=new ArrayList<>();
         menuButton=new MenuButton();

    }

    public void setHalls(Hall hall) {
        halls.add(hall);


    }
    public boolean HallExist(Hall hall){
        for (Hall h:halls)
            if (h.getHallname().equals(hall.getHallname()))return true;
            return false;
    }
    public void addHall(String text){
        MenuItem hallitem=new MenuItem(text);
        if (!menuButton.getItems().contains(hallitem)){
        hallitem.setOnAction(event1 -> menuButton.setText(text));
        menuButton.getItems().add(hallitem);}
    }

    public ArrayList<Hall> getHalls() {
        return halls;
    }
    public void removeHall(){
        if (menuButton.getText()!=null)
        for (MenuItem item:menuButton.getItems()){
            if (menuButton.getText().equals(item.getText())){
                menuButton.getItems().remove(item);
                menuButton.setText(null);
                removeH(item.getText());
                break;
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getMunite() {
        return munite;
    }
    public MenuButton getMenuButton(){
        if (!halls.isEmpty()){
        for (Hall h:halls){
            MenuItem menuItem=new MenuItem(h.getHallname());
            menuItem.setOnAction(event -> menuButton.setText(menuItem.getText()));
            menuButton.getItems().add(menuItem);
        }}return  menuButton;
    }
    public MenuButton RMenuBUtton(){
        return menuButton;
    }
    public Hall getHall(){
       for (Hall h:halls){
           if (menuButton.getText().equals(h.getHallname()))return h;
       }
       return null;
    }
    private void removeH(String s){
        int temp=-1;
        for (Hall hall:halls){
            if (s.equals(hall.getHallname())) temp=halls.indexOf(hall);

        }
        if (temp!=-1)halls.remove(temp);
    }


}

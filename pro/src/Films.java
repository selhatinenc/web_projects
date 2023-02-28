package com.example.demo;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class Films {
    private ArrayList<Movies> Films;
    private MenuButton menuButton;
    public  Films(){
        Films=new ArrayList<>();
        menuButton=new MenuButton();

    }
    public void setFilms(Movies film) {

        Films.add(film);
    }
    public void removefilm(int index){
       Films.remove(index);
       setMenuButton();
    }

    public ArrayList<Movies> getFilms() {
        return Films;
    }

    public MenuButton setMenuButton(){
        menuButton.getItems().clear();
        for (Movies f:Films){
            MenuItem item=new MenuItem(f.getName());
            item.setOnAction(event -> menuButton.setText(item.getText()));
            menuButton.getItems().add(item);
        } return menuButton;
    }
    public MenuButton getMenuButton(){
        return menuButton;
    }
    public Movies getMovie(){
        for (Movies mv:Films){
            if (menuButton.getText().equals(mv.getName()))return mv;
        }
        return null;
    }
    public boolean ContainFilm(Movies movies){
        for (Movies mv:Films)
            if (mv.getName().equals(movies.getName()))return true;
        return false;
    }

}

package com.example.demo;

import javafx.scene.image.Image;

public class Seat {
 private    boolean free=true;
  private   int row,column,price;
  private String owner,film,hall;
  private int disp=0;
    private Image image;
    public Seat(String film,String hall,Integer row,Integer column,String owner,int price){
        this.row=row;this.column=column;this.film=film;this.hall=hall;this.owner=owner;this.price=price;
        if (!owner.equals("null"))setFull();
    }


    public void setFull( ) {
        this.free = false;
    }
    public void setFree( ) {
        this.free = true;
    }
    public void setDisp(int disp){
        this.disp=disp;
    }
    public void  setClubMemberPrice(){

    }

    public Image getImage() {
        return image;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isFree() {
        return free;
    }

    public String getOwner() {
        return owner;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice(String s) {

        return price;
    }

    public String getFilm() {
        return film;
    }

    public String getHall() {
        return hall;
    }
    public  String ToString(){
        return "seat"+"\t"+getFilm()+"\t"+getHall()+"\t"+getRow()+"\t"+column+"\t"+owner+"\t"+price+"\n";
    }
}

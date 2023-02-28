package com.example.demo;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.MalformedURLException;

public class Hall {
    private String movie;
    private  String p;
    private int price;
    private int row,column;
    private Seat[][] seats;
    private String owner;
    private Label label1,label2;
    private String hallname;
    private VBox vBox;
    private int dis;
    private   ImageView[][] imageViewslist;
    private   Image[][] Image;

    public Hall(String Mv,String hallname,int price,int row,int column,int perc){
        p="";
        dis=perc;
        this.hallname=hallname;
       imageViewslist=new ImageView[row][column];
        Image=new Image[row][column];

        label1=new Label(); label2=new Label();
        movie=Mv;this.price=price;this.row=row;this.column=column;
        seats=new Seat[row][column];

    }
    public void setSeat(int R,int C,Seat seatt){
       seats[R][C]=seatt;
        seats[R][C].setDisp(dis);
    }
    public void SetAllSeat(){
        for (int i = 0; i <row ; i++) {
            for (int j = 0; j <column ; j++) {
                Seat dseat=new Seat(movie,hallname,row,column,"null",price);
                dseat.setDisp(dis);
                seats[i][j]=dseat;
            }
        }
    }

    public String getMovie() {
        return movie;
    }

    public int getMunite() {
        return price;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
    public void setLabel2(){
        label2.setText("");
    }
    public VBox setSeats() throws MalformedURLException {
        label2.setText("");

         vBox=new VBox();
        Image  image = new Image(new File("src/main/resources/empty_seat.png").toURI().toURL().toString(),false);
       Image full= new Image(new File("src/main/resources/reserved_seat.png").toURI().toURL().toString());
        for (int i = 0; i < row; i++) {
            HBox hBox =new HBox();
            for (int j = 0; j <column ; j++) {
                ImageView imageview;
                if (seats[i][j].isFree()){
                    Image[i][j]=image;
                            imageview=new ImageView(image);

                }
                else{                    Image[i][j]=full;

                    imageview=new ImageView(full);

                }
                imageViewslist[i][j]=imageview;
                imageview.setFitHeight(500/(row>=column?row:column));
                imageview.setFitWidth(500/(row>=column?row:column));
                int finalJ = j;
                int finalI = i;

                hBox.getChildren().add(imageview);

                imageview.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (seats[finalI ][finalJ ].isFree()){
                            label1.setText("Not Bought yet");
                        }else{
                            if (p.equals(true)){
                                label1.setText("Bought by "+seats[finalI ][finalJ ].getOwner()+" for "+((price*(100-dis))/100)+" TL Succesfully");
                            }
                            else {
                                label1.setText("Bought by "+seats[finalI ][finalJ ].getOwner()+" for "+price+" TL Succesfully");
                            }
                        }
                    }
                });
                imageview.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        label1.setText("");
                    }
                });
                imageview.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        if (seats[finalI ][finalJ ].isFree()){
                            seats[finalI ][finalJ ].setFull();
                            try {
                                imageview.setImage(new Image(new File("src/main/resources/reserved_seat.png").toURI().toURL().toString()));
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            seats[finalI ][finalJ ].setOwner(owner);
                            label2.setText("Seat at "+(finalI+1)+"-"+(finalJ+1)+" is bought for "+ seats[finalI ][finalJ ].getOwner());
                        }
                        else{
                            seats[finalI ][finalJ ].setFree();
                            try {
                                imageview.setImage(new Image(new File("src/main/resources/empty_seat.png").toURI().toURL().toString()));
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            seats[finalI ][finalJ ].setOwner("null");
                            label2.setText("");

                        }
                    }
                });

            }
            vBox.getChildren().add(hBox);
        }
        return vBox;
    }
    public VBox getVbox(String user,String username){
        if (user.equals("user")){
            for (int i = 0; i <row ; i++) {
                for (int j = 0; j <column ; j++) {
                    if (!seats[i][j].getOwner().equals("null")&&!seats[i][j].getOwner().equals(username)) imageViewslist[i][j].setDisable(true);
                }
            }
        }
        else {
            for (int i = 0; i <row ; i++) {
                for (int j = 0; j <column ; j++) {
                    if (imageViewslist[i][j].isDisabled()) imageViewslist[i][j].setDisable(false);
                }
            }
        }


        return vBox;
    }
    public void setOwner(String owner) {
        this.owner = owner;
        this.p="";
    }
    public void setOwner(String owner,String p) {
        this.owner = owner;
        this.p=p;
    }

    public Label getLabel1() {
        return label1;
    }

    public Label getLabel2() {
        return label2;
    }

    public String getHallname() {
        return hallname;
    }

    public int getPrice() {
        return price;
    }
    public String ToStringSeat(){
        String s="";
        for (int i = 0; i <row ; i++) {
            for (int j = 0; j <column ; j++) {
//                System.out.println("i:"+i+"j: "+j);
                s+=seats[i][j].ToString();
            }
        }return s;
    }
}

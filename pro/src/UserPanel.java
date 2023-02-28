package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserPanel extends GridPane {
    public  UserPanel(Movies movie,Button back,Button ok){

        MediaPlayerController mediaPlayerController=new MediaPlayerController(movie.getPath());
        mediaPlayerController.pauseVideo(new ActionEvent());
        Button startstop=new Button("|>"),fiveforward=new Button(">>"),fiveback=new Button("<<"),gostart=new Button("|<<"), twox=new Button("2X"),halfx=new Button("0.5");
       /* Panel panel=new Panel();
        panel.getChildren().add(mediaPlayerController);
        panel.setPadding(new Insets(20)); */

        HBox Bhbox=new HBox();
        Bhbox.getChildren().addAll(back, movie.RMenuBUtton(),ok);
        Slider Svol=mediaPlayerController.getVolumeSlider();
        Svol.setValue(30);
        VBox vBox=new VBox();
        vBox.getChildren().addAll(startstop,fiveback,fiveforward,gostart,twox,halfx,Svol);
        HBox hbox=new HBox();
        hbox.getChildren().addAll(mediaPlayerController,vBox);


        this.addRow(0,new Label(movie.getName()+"("+movie.getMunite()+")"));
        this.addRow(1,hbox);
        this.addRow(2,Bhbox);
        startstop.setOnAction(event -> {
            if (mediaPlayerController.getPause() == false) {
                startstop.setText("|>");
                mediaPlayerController.pauseVideo(event);
            } else {
                startstop.setText("||");
                mediaPlayerController.playVideo(event);
            }
        });
        fiveback.setOnAction(event -> {
            mediaPlayerController.back5(event);
        });
        fiveforward.setOnAction(event -> {
            mediaPlayerController.skip5(event);
        });
        gostart.setOnAction(event -> {
            mediaPlayerController.stopVideo(event);
        });

        Svol.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (event==(Svol.getOnSwipeDown()))mediaPlayerController.VolumeSet();
                else if(event==Svol.getOnSwipeUp())mediaPlayerController.VolumeSet2();
            }
        });

        twox.setOnAction(event -> mediaPlayerController.furtherSpeedUpVideo(event));
        halfx.setOnAction(event -> mediaPlayerController.furtherSlowDownVideo(event));


        this.setMinSize(850,500);
    }


}

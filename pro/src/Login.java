package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class Login extends Application {
    private StackPane root = new StackPane();
    private Stage stage;

    @Override
    public void init() {
        Button Bsignup = new Button("SİGN UP");Button Blogin = new Button("LOGİN");
        VBox vBox = new VBox();

        vBox.setSpacing(8);
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.getChildren().addAll(
                new Label("Welcome to HUCS Cinema Reservation\nPlease enter your credential below and click LOGIN" +
                        "\nYou can create an account by clicking SİGN UP\nUsername:"),
                new TextField(),
                new Label(" Password"),
                new PasswordField(),
                Bsignup,
                Blogin);
        root.getChildren().addAll(vBox);
        Blogin.setOnAction(actionEvent -> {
            if(stage!=null){




            }
        });

        Bsignup.setOnAction(actionEvent-> {
            if(stage!=null){

                stage.requestFocus();
                return;
            }
            stage = new Stage();
            StackPane stackPane = new StackPane();
            stage.setScene(new Scene(stackPane, 200,200));
            stage.show();
        });
    }


    @Override
    public void start(Stage primaryStage) throws Exception  {
        init();
        Scene scene = new Scene(root,400,600);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Sign up");
        primaryStage.setAlwaysOnTop(true);
    }

    public Login( ) throws Exception {
        stage=new Stage();
        start(stage);
    }
}
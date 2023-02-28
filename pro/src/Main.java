package com.example.demo;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {
    private StackPane root = new StackPane();
    Button Bsignup,Blogin;
    private String title;
    public int discpercentage,maxerror,bloctime,maxerrortry;
    private static ArrayList<Hall> HallsArray=new ArrayList<>();
    private static Users users;
    private static ArrayList<Movies> FilmsArray=new ArrayList<>();
    private static ArrayList<Seat> SeatArray=new ArrayList<>();
    private ObservableList<User> data ;
    private BufferedInputStream bf;
    private MediaPlayer MusicPlayer;
    private  Films films;
    private  FileWriter fileWriter;
    private Image IconImage;
    private String user;
    private boolean isclubmember;
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException, ClassNotFoundException {



        launch(args);


    }
    @Override    @SuppressWarnings("unchecked")

    public void stop(){
        String backup=new File("src/main/resources/assets/data/backup.dat").toString();
        try {
            fileWriter=new FileWriter(backup);
            for (User u:users.getUser())fileWriter.write("user"+"\t"+u.getName()+"\t"+u.getPasword()+"\t"+u.getPrompter()+"\t"+u.getAdmin()+"\n");
            for (Movies film:films.getFilms()){
                fileWriter.write("film"+"\t"+film.getName()+"\t"+film.getPath()+"\t"+film.getMunite()+"\n");
                for (Hall h:film.getHalls()){
                    fileWriter.write("hall"+"\t"+h.getMovie()+"\t"+h.getHallname()+"\t"+h.getPrice()+"\t"+h.getRow()+"\t"+h.getColumn()+"\n");
                    fileWriter.write(h.ToStringSeat());
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        // Save file
    }
    public void init(Stage stage) {

        Bsignup = new Button("SİGN UP"); Blogin = new Button("LOGİN");
        VBox vBox = new VBox();
        TextField textField=new TextField();
        PasswordField passwordField=new PasswordField();
        Label errorLabel=new Label("");
        Label blockLabel=new Label("");
        vBox.setSpacing(8);
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.getChildren().addAll(
                new Label("Welcome to HUCS Cinema Reservation\n\nPlease enter your credential below and click LOGIN" +
                        "\n\nYou can create an account by clicking SİGN UP\n\nUsername:"),
                textField,
                new Label(" Password"),
               passwordField,
                Blogin,
                Bsignup,
                errorLabel,
                blockLabel);
        root=new StackPane(); root.getChildren().addAll(vBox);
        stage = new Stage();
        Scene scene = new Scene(root,400,600);
        stage.setScene(scene);
        stage.show();
        stage.setTitle(title);
        stage.getIcons().add(IconImage);

        Stage finalStage = stage;
        Blogin.setOnAction(actionEvent -> {

            String userrname=textField.getText();
            if (maxerrortry==0){
                blockLabel.setText("wait "+bloctime+" please");
                delay(1000*bloctime, () -> blockLabel.setText(""));
                maxerrortry++;

            }
            else{
            if (users.Isexist(userrname)){
                maxerrortry=maxerror;
                    isclubmember=users.IsClubMember(userrname);

                    if (users.Passwordmatch(userrname,Hashingpassword(passwordField.getText()))){
                        user=textField.getText();
                        if (users.IsAdmin(userrname)) {
                    finalStage.close();
                    Admin(finalStage,userrname);}
                    else{

                       finalStage.close(); UserPanel(finalStage,userrname);
                    }
                }else{
                        maxerrortry--;
                        PlayMusic(); errorLabel.setText("Wrong password or username");
                }
            }
            else{
                maxerrortry--;
                errorLabel.setText("Wrong password or username");
                PlayMusic();
            }}




        });
        Bsignup.setOnAction(actionEvent-> {
            finalStage.close();;
         signin(finalStage);


        });


    }

    @Override    @SuppressWarnings("unchecked")

    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
      films=new Films();        data=FXCollections.observableArrayList();
        IconImage=new Image(new File("src/main/resources/assets/icons/logo.png").toURI().toURL().toString());
        primaryStage.getIcons().add(IconImage);
        users=new Users();
       Media sound = new Media("file:/C:/Users/as_as/OneDrive/Masaüstü/error.mp3");
        MusicPlayer = new MediaPlayer(sound);

        ReadProperties readProperties=new ReadProperties();
        maxerror=readProperties.getMaxblocked();
        discpercentage=readProperties.getDiscountpercentage();
        title=readProperties.getTitle();
        bloctime=readProperties.getBlocktime();
        maxerrortry=maxerror;

        try {
            readbackup();
            films.setMenuButton();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        init(primaryStage);




    }


    @SuppressWarnings("unchecked")

    private  void signin(Stage stage){
        Bsignup = new Button("SİGN UP"); Blogin = new Button("LOGİN");
        VBox vBox = new VBox();
        PasswordField passwordField1=new PasswordField();
        PasswordField passwordField=new PasswordField();TextField textField=new TextField();
        Label errorlabel=new Label(" ");

        vBox.setSpacing(8);
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.getChildren().addAll(
                new Label("Welcome to HUCS Cinema Reservation\nPlease enter your credential below and click LOGIN" +
                        "\nYou can create an account by clicking SİGN UP\nUsername:"),
                textField,
                new Label(" Password"),
                passwordField, new Label(" Password"),passwordField1,
                Bsignup,
                Blogin,
                new Label(),
                errorlabel);
        root=new StackPane(); root.getChildren().addAll(vBox);
        stage = new Stage();
        Scene scene = new Scene(root,400,600);
        stage.setScene(scene);
        stage.show();
        stage.setTitle(title);
        stage.getIcons().add(IconImage);


        Stage finalStage = stage;
        Bsignup.setOnAction(event -> {
            String userrname=textField.getText();
            if (!passwordField.getText().equals("") && !userrname.equals("")){
            if (users.Isexist(userrname)){
                PlayMusic();
                errorlabel.setText("This user already exist");
            }
            else{
                if (passwordField.getText().toString().equals(passwordField1.getText().toString())){
                    users.setUser(new User(userrname,Hashingpassword(passwordField.getText()),"false","false"));
                    errorlabel.setText("User created succesfully");
                }else{
                    PlayMusic();
                    errorlabel.setText("Passwords don't match");
                }
            }}
            else{
                PlayMusic();
                errorlabel.setText("Enter UserName and Password");
            }
        });
        Blogin.setOnAction(actionEvent -> {
            finalStage.close();
            init(finalStage);

        });


    }
    @SuppressWarnings("unchecked")
    private void Admin(Stage stage,String username){




        Button Bokey=new Button("OK");
        Button Badd=new Button("Add Film");
        Button Bremove=new Button("Remove Film");
        Button Bedit=new Button("Edit Users");
        Button Blogout=new Button("LOG OUT");

        AtomicReference<MenuButton> menuButton= new AtomicReference<>(films.getMenuButton());
        GridPane root = new GridPane();
        // Add the children to the VBox
        root.addRow(0,new Label("Welcome to Admin(Admin-Club Member)\n You can either select movie or do edit"));
        root.addRow(1, menuButton.get(),Bokey);
        root.addRow(2,Badd,Bremove,Bedit);
        root.addRow(3,Blogout);
        Badd.setOnAction(ActionEvent ->{
            Baddfunc(stage);
        });
        Bremove.setOnAction(ActionEvent ->{
            final MenuButton finalmenuB= menuButton.get();

            if (finalmenuB.getText()!=null){
                int temp=-1;
                for (Movies flm:films.getFilms()){
                    if (flm.getName().equals(finalmenuB.getText())){
                       temp=films.getFilms().indexOf(flm);
                    }
                }
            if (temp!=-1){
                films.removefilm(temp); menuButton.set(films.getMenuButton());
                menuButton.get().setText(null);
            }




        }});
        Bedit.setOnAction(ActionEvent ->{
            EditUsers(stage,username);
        });
        Blogout.setOnAction(ActionEvent ->{
            stage.close();
        init(stage);
        });
        Bokey.setOnAction(ActionEvent ->{
            if (!menuButton.get().getText().equals("")){
                Player(stage, films.getMovie());

            }
        });

        // Set the Size of the VBox
        root.setMinSize(350, 250);

        /*
         * Set the padding of the VBox
         * Set the border-style of the VBox
         * Set the border-width of the VBox
         * Set the border-insets of the VBox
         * Set the border-radius of the VBox
         * Set the border-color of the VBox
         */
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        // Create the Scene
        Scene scene = new Scene(root);
        // Add the scene to the Stage
        stage.setScene(scene);
        // Set the title of the Stage
        stage.setTitle(title);
        // Display the Stage
        stage.show();

    }  // Helper Method
    @SuppressWarnings("unchecked")

    private void EditUsers(Stage stage,String username) {
        TableView<User> table = new TableView<>();
        table.setEditable(true);
        data.clear();
        for (User u:users.getUser())
        {
            if (!u.getName().equals(username))
                data.add(u);}

            HBox hb = new HBox();
        Scene scene = new Scene(new Group());
        stage.setWidth(450);
        stage.setHeight(550);


        TableColumn firstNameCol = new TableColumn("Username");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TableColumn lastNameCol = new TableColumn("Club Member");
        lastNameCol.setVisible(true);
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("prompter"));
        TableColumn adminCol = new TableColumn("Admin");
        adminCol.setMinWidth(100);
        adminCol.setCellValueFactory(
                new PropertyValueFactory<>("admin"));
        TableColumn fourthCol = new TableColumn("fourth");
        fourthCol.setVisible(false);
        fourthCol.setMinWidth(100);
        fourthCol.setCellValueFactory(
                new PropertyValueFactory<>("pasword"));
        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol,adminCol,fourthCol);


        final Button backbutton = new Button("Back");
        final Button Bclubmember = new Button("Promote/Demote Club Member");
        final Button BAdmin = new Button("Promote/Demote Admin");


        hb.getChildren().addAll(backbutton,Bclubmember,BAdmin);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

        backbutton.setOnAction((ActionEvent e) -> {
            // data.add(new Person("görkem","false","true","sdfdsf "));
            Admin(stage,user);
        });
        Bclubmember.setOnAction(actionEvent -> {
            if (table.getSelectionModel().getSelectedItem()!=null)
                table.getSelectionModel().getSelectedItem().setPrompter("s");
                EditUsers(stage,user);
        });
        BAdmin.setOnAction(actionEvent -> {
            if (table.getSelectionModel().getSelectedItem()!=null)

                table.getSelectionModel().getSelectedItem().setAdmin("s");
            EditUsers(stage,user);

        });

    }
    @SuppressWarnings("unchecked")
    private  void RemoveHal(Stage stage,Movies movie){
        Button back=new Button("BACK"), ok=new Button("OK");


        GridPane gridPane=new GridPane();
        gridPane.addRow(0,new Label());
        gridPane.addRow(1,movie.RMenuBUtton());
        gridPane.addRow(2,back,ok);
        back.setOnAction(event -> Player(stage,movie));
        ok.setOnAction(event ->{if(!movie.RMenuBUtton().getText().equals("")) movie.removeHall();movie.getMenuButton();}) ;
        gridPane.setMinSize(250,200);

        Scene scene = new Scene(gridPane);
        stage.setTitle(title);

        stage.setScene(scene);
        stage.show();

    }
    @SuppressWarnings("unchecked")
    private void Baddfunc(Stage stage ) {
        TextField f1=new TextField(),f2=new TextField(),f3=new TextField();
        Button b1=new Button("Back"),b2=new Button("OK");
        Label errorLabel=new Label(" ");


        GridPane root = new GridPane();
        // Add the children to the VBox
        root.addRow(0,new Label("Please give name, relative path of trailer and duration of movie"));
        root.addRow(1,new Label("Name:"),f1);
        root.addRow(2,new Label("Trailer(path):"),f2);
        root.addRow(3,new Label("Duration(m):"),f3);
        root.addRow(4,b1,b2);
        root.addRow(5,errorLabel);
        b1.setOnAction(ActionEvent ->{
            stage.close();
            Admin(stage,user);
        });
        b2.setOnAction(event -> {
        if (f1.getText().equals("")){errorLabel.setText("Please enter a name"); PlayMusic();    }
        else if (f2.getText().equals("")){errorLabel.setText("Please enter trailer path");; PlayMusic();    }
        else if (f3.getText().equals("")){errorLabel.setText("Please enter duration");;    PlayMusic(); }
        else {
            String duration=f3.getText();

            try {
                int drt=Integer.valueOf(duration);
                Movies mNew=new Movies(f1.getText(),f2.getText(),drt);
                if (films.ContainFilm(mNew)){ errorLabel.setText("This Film already exist"); PlayMusic();}
                else {
                    errorLabel.setText("Film added successfully");
                    films.setFilms(mNew);
                    films.setMenuButton();
                }
            }
            catch (Exception e){
                errorLabel.setText("Please enter validate duration");
            }
        }

        });


        // Set the Size of the VBox
        root.setMinSize(350, 250);

        /*
         * Set the padding of the VBox
         * Set the border-style of the VBox
         * Set the border-width of the VBox
         * Set the border-insets of the VBox
         * Set the border-radius of the VBox
         * Set the border-color of the VBox
         */
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        // Create the Scene
        Scene scene = new Scene(root);
        // Add the scene to the Stage
        stage.setScene(scene);
        // Set the title of the Stage
        stage.setTitle("A work");
        // Display the Stage
        stage.show();

    }


    @SuppressWarnings("unchecked")
    private  void readbackup() throws IOException, ClassNotFoundException {


      //   bf = new BufferedInputStream (new FileInputStream("news.dat"));
       String str=new File("src/main/resources/assets/data/backup.dat").toString();


        ArrayList<String > arr= (ArrayList<String>) Files.readAllLines(Paths.get(str));
        for(String s:arr){
            String first=s.split("\t")[0];
            String[] st=s.split("\t");
            if(first.equals("user"))users.setUser(new User(st[1],st[2],st[3],st[4]));
            else if(first.equals("hall"))HallsArray.add(new Hall(st[1],st[2],Integer.valueOf(st[3]),Integer.valueOf(st[4]),Integer.valueOf(st[5]),discpercentage));
            else if(first.equals("film")){FilmsArray.add(new Movies(st[1],st[2],Integer.valueOf(st[3]))); }
            else if(first.equals("seat"))SeatArray.add(new Seat(st[1],st[2],Integer.valueOf(st[3]),Integer.valueOf(st[4]),st[5],Integer.valueOf(st[6])));

        }
        users.setPerson();
        for (Movies film:FilmsArray){
            for (Hall hall:HallsArray){
                if (hall.getMovie().equals(film.getName()))film.setHalls(hall);
            }
        }
        for (Movies f:FilmsArray){
            f.getMenuButton();
        }
        for (Seat seatt:SeatArray)
        for (Movies film:FilmsArray)
            if (seatt.getFilm().equals(film.getName()))
            for (Hall h:film.getHalls())
                if (seatt.getHall().equals(h.getHallname())){

                    h.setSeat(seatt.getRow(),seatt.getColumn(),seatt);}

        for (Movies film:FilmsArray){
            for (Hall hall:HallsArray)
                try {
                    hall.setSeats();
                }
            catch (Exception e){

            }

        }

        for (Movies film:FilmsArray){
            films.setFilms(film);
        }







    }
    @SuppressWarnings("unchecked")
    private void Player(Stage stage, Movies movie){

        GridPane root = new GridPane();
        MediaPlayerController mediaPlayerController=new MediaPlayerController(movie.getPath());
        mediaPlayerController.pauseVideo(new ActionEvent());
        Button startstop=new Button("|>"),fiveforward=new Button(">>"),fiveback=new Button("<<"),gostart=new Button("|<<");
        Button back=new Button("Back"), addhall=new Button("Add Hall"), removehal=new Button("Remove Hall"),ok=new Button("OK"), twox=new Button("2X"),halfx=new Button("0.5");
       /* Panel panel=new Panel();
        panel.getChildren().add(mediaPlayerController);
        panel.setPadding(new Insets(20)); */

        HBox Bhbox=new HBox();
        Bhbox.getChildren().addAll(back,addhall,removehal, movie.RMenuBUtton(),ok);
        Slider Svol=mediaPlayerController.getVolumeSlider();
        Svol.setValue(30);
        VBox vBox=new VBox();
        vBox.getChildren().addAll(startstop,fiveback,fiveforward,gostart,twox,halfx,Svol);
        HBox hbox=new HBox();
        hbox.getChildren().addAll(mediaPlayerController,vBox);


        root.addRow(0,new Label(movie.getName()+"("+movie.getMunite()+")"));
        root.addRow(1,hbox);
        root.addRow(2,Bhbox);
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
        back.setOnAction(event -> {
            mediaPlayerController.pauseVideo(event);
            stage.close();
            Admin(stage,user);
        });
        twox.setOnAction(event -> mediaPlayerController.furtherSpeedUpVideo(event));
        halfx.setOnAction(event -> mediaPlayerController.furtherSlowDownVideo(event));
        ok.setOnAction(event -> {

                if (movie.getHall()!=null){
                    mediaPlayerController.pauseVideo(event);
                    ChooseSeat(stage,movie.getHall(),movie);
                }} );
        addhall.setOnAction(event ->{mediaPlayerController.pauseVideo(event); AddHall(stage,movie); } );
        removehal.setOnAction(event -> {stage.close(); RemoveHal(stage,movie);});
        root.setMinSize(950,500);

        Scene scene = new Scene(root);
        stage.setTitle(title);

        stage.setScene(scene);
        stage.show();
    }

    @SuppressWarnings("unchecked")
    private void ChooseSeat(Stage stage, Hall hall,Movies movie) {
        GridPane gridPane=new GridPane();
        hall.setOwner(user);
        Button back=new Button("Back");

        gridPane.addRow(0,new Label(movie.getName()+"("+movie.getMunite()+") Hall:"+hall.getHallname()));

            gridPane.addRow(1,hall.getVbox("admin",""));

        gridPane.addRow(2,users.setMenuButton(hall));
        gridPane.addRow(3,hall.getLabel1());
        gridPane.addRow(4,hall.getLabel2());
        gridPane.addRow(5,back);
        back.setOnAction(event -> Player(stage, movie));
        gridPane.setMinSize(600,600);
        gridPane.setPadding(new Insets(30,30,10,30));
        Scene scene = new Scene(gridPane);
        stage.setTitle(title);

        stage.setScene(scene);
        stage.show();
    }

    @SuppressWarnings("unchecked")
    private void AddHall(Stage stage,Movies movie) {
        HBox hBox1=new HBox(),hBox2=new HBox();
        MenuButton menuButton1=new MenuButton(),menuButton2=new MenuButton();
        MenuItem[] items=new MenuItem[8];
        MenuItem[] items2=new MenuItem[8];

        Button button1=new Button("Back"),buttonOk=new Button("OK");
        Label errorLabel=new Label("Error label");

        TextField textField1=new TextField(),textField2=new TextField();
        for (int i = 0; i <8 ; i++) {
            MenuItem item1=new MenuItem(String.valueOf(i+3));

            int finalI = i;
            item1.setOnAction(event -> menuButton1.setText(String.valueOf(finalI +3)));
            items[i]=item1;
            MenuItem item2=new MenuItem(String.valueOf((i+3)));
            item2.setOnAction(event -> menuButton2.setText(String.valueOf(finalI +3)));
            items2[i]=item2;

            menuButton1.getItems().add(items[i]);
            menuButton2.getItems().add(items2[i]);

        }

        hBox1.getChildren().addAll(new Label("Row:"),menuButton1);
        hBox2.getChildren().addAll(new Label("Column:"),menuButton2);

        GridPane gridPane=new GridPane();
        gridPane.addRow(0,new Label(movie.getName()+"("+movie.getMunite()+")"));
        gridPane.addRow(1,hBox1);
        gridPane.addRow(2,hBox2);
        gridPane.addRow(3,new Label("Name:"),textField1);
        gridPane.addRow(4,new Label("Price:"),textField2);
        gridPane.addRow(5,button1,buttonOk);
        gridPane.addRow(6,errorLabel);

        buttonOk.setOnAction(event -> {
            if (menuButton1.getText().equals("")){
                errorLabel.setText("Choose Row");
                PlayMusic();

            }
            else if (menuButton2.getText().equals("")){
                errorLabel.setText("Choose Column");
                PlayMusic();

            }
            else if (textField1.getText().equals("")){
                errorLabel.setText("Enter a validate name");
                PlayMusic();

            }
            else if (textField2.getText().equals("")){
                errorLabel.setText("Enter a price");
                PlayMusic();


            }
            else{
                try{

                    Hall newHall=new Hall(movie.getName(),textField1.getText(),Integer.valueOf(textField2.getText()),Integer.valueOf(menuButton1.getText()),Integer.valueOf(menuButton2.getText()) ,discpercentage);
                    if (movie.HallExist(newHall)){
                        errorLabel.setText("The Hall already exist");
                        PlayMusic();
                    }else{
                        try {
                            newHall.SetAllSeat();
                            newHall.setSeats();
                            movie.setHalls(newHall);
                            movie.addHall(textField1.getText());
                            errorLabel.setText("The Hall added successfully");
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }catch (Exception e){
                    PlayMusic();
                errorLabel.setText("Please enter a validate price");
                }

            }


        });
        button1.setOnAction(event ->{
          stage.close();Player(stage, movie);
        } );
        Scene scene = new Scene(gridPane);
        stage.setTitle("Media Player");

        stage.setScene(scene);
        stage.show();


    }

    @SuppressWarnings("unchecked")

private void PlayMusic(){
        MusicPlayer.seek(Duration.ZERO);
        MusicPlayer.play();
}
@SuppressWarnings("unchecked")

    public String Hashingpassword(String pass){
 /*   MessageDigest md = null;
    try {
        md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(pass.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while (hashtext.length() < 32) {
            hashtext +=  hashtext;
        }
        hashtext= Base64.getEncoder().encodeToString(hashtext.getBytes());

        return hashtext;
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
        return null;
    }*/




    byte [ ] bytesOfPassword = pass.getBytes( StandardCharsets.UTF_8) ;
    byte [ ] md5Digest = new byte [ 0 ] ;
    try {
        md5Digest = MessageDigest.getInstance( "MD5" ) .digest ( bytesOfPassword ) ;
    } catch ( NoSuchAlgorithmException e ) {
        return  null ;
    }
    return Base64 . getEncoder ( ) . encodeToString ( md5Digest ) ;


}    @SuppressWarnings("unchecked")

    public void UserPanel(Stage stage,String username){
    AtomicReference<MenuButton> menuButton= new AtomicReference<>(films.getMenuButton());
    Button log_out=new Button("Log Out"),ok=new Button("OK");
    Label label;
    if (isclubmember)  label=new Label("Welcome to ClubMember"+username+"!\n Select a film then click OK to Continue.");
    else  label= new Label("Welcome to "+username+"!\n Select a film then click OK to Continue.");
    GridPane gridPane= new GridPane();
    gridPane.addRow(0,label);
    gridPane.addRow(1,menuButton.get(),ok);
    gridPane.addRow(2,new Label(),log_out);
    log_out.setOnAction(event ->{ stage.close(); init(stage);} );
    ok.setOnAction(event ->{ if (!menuButton.get().getText().equals("")){stage.close(); UserPlayer(stage,films.getMovie(),username);}});
   gridPane.setMinSize(300,300);
    Scene scene = new Scene(gridPane);
    stage.setTitle(title);

    stage.setScene(scene);
    stage.show();
}    @SuppressWarnings("unchecked")

    public  void UserPlayer(Stage stage,Movies movie,String username){
        Button back=new Button("Back"),ok=new Button("OK");
        GridPane UserPane=new UserPanel(movie,back,ok);
        back.setOnAction(event ->{ stage.close(); UserPanel(stage,username);});
        ok.setOnAction(event -> {Hall hall=movie.getHall();if (hall!=null){stage.close(); UserChooseSeat(stage,movie,hall,username);}} );
        UserPane.setMinSize(900,500);
        Scene scene = new Scene(UserPane);
    stage.setTitle(title);

    stage.setScene(scene);
    stage.show();

}    @SuppressWarnings("unchecked")


    private void UserChooseSeat(Stage stage,Movies movie,Hall hall,String username) {
        hall.setOwner(username);
        hall.setLabel2();
        GridPane gridPane=new GridPane();

        Button back=new Button("Back");

        gridPane.addRow(0,new Label(movie.getName()+"("+movie.getMunite()+") Hall:"+hall.getHallname()));

        gridPane.addRow(1,hall.getVbox("user",username));

        gridPane.addRow(2,    new Label());
        gridPane.addRow(3,hall.getLabel1());
        gridPane.addRow(4,hall.getLabel2());
        gridPane.addRow(5,back);
        back.setOnAction(event -> UserPlayer(stage,movie,username));
        gridPane.setMinSize(600,600);
        gridPane.setPadding(new Insets(30,30,10,30));
        Scene scene = new Scene(gridPane);
        stage.setTitle(title);

        stage.setScene(scene);
        stage.show();
    }    @SuppressWarnings("unchecked")

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }
}



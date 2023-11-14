package com.example.myissuesinterface;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class StartApplication extends Application {
    static String sessionID = "-1";

    private Stage stage;
    private Scene scene;
    private Parent parent;

    private static final String host = "localhost";
    private static final int port =6666 ;
    private static Socket connection;
    @Override
    public void start(Stage stage) throws IOException {
        //Interface
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("MyIssues - 2MB");
        stage.setScene(scene);
        InputStream stream = new FileInputStream("src/main/resources/img/Logo.png");
        Image icon = new Image(stream);
        stage.getIcons().add(icon);
        stage.show();
    }

    //Switch from views
    public void switchTo(ActionEvent event,String view) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(view)));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //Connection to Server
        try {
            connection = serverConnection();
        }catch (Exception e){System.out.println(e);}
        launch();
    }


    // Server Methods
    public static Socket serverConnection() throws IOException {
        System.out.println("Connecting to Server " + host + "at port: " + port);
        Socket s=new Socket(host,port);
        System.out.println("Connection Server DONE");
        return s;
    }

    String str2;
    public String sendToServer(String send) throws IOException {
        DataInputStream din=new DataInputStream(connection.getInputStream());
        DataOutputStream dout=new DataOutputStream(connection.getOutputStream());
        dout.writeUTF( sessionID + " " + send);
        dout.flush();
        str2=din.readUTF();
        if(str2.contains("Successfully login -")){
            sessionID = sessionID.replace("-1", str2.replace("Successfully login - ", ""));
            str2 = "Successfully login";
            System.out.println ("ClientSessionID changed to: " + sessionID);
        }
        System.out.println(str2);
        return str2;
    }
}
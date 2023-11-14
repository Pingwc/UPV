package com.example.myissuesinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginController {
    @FXML
    private Button register = new Button();
    private Stage stage = new Stage();
    private StartApplication app = new StartApplication();
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    public void handleButtonRegister(ActionEvent event) throws Exception {
        app.switchTo(event, "Register.fxml");
    }


    @FXML
    public void handleButtonLogin(ActionEvent event) throws Exception {
        String aux = "";
        aux = app.sendToServer("login" + " "+ username.getText()+ " " + password.getText());
        if(aux.contains("Successfully login"))
            app.switchTo(event, "Home.fxml");
        else if(aux.contains("java")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid username or password");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must provide username and password");
            alert.showAndWait();
        }

    }


}
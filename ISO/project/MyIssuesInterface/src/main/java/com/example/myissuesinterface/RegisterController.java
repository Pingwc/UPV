package com.example.myissuesinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public TextField email;
    public TextField password;
    public TextField username;
    public TextField surname;

    private StartApplication app = new StartApplication();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void handleButtonRegister(ActionEvent event) throws IOException {
        String aux = "";
        aux = app.sendToServer("addUser "+ username.getText()+ " " + surname.getText()
                + " " + email.getText() + " " + password.getText() );
        if(aux.contains("Has been created successful")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You have successfully registered!");
            alert.setHeaderText("Successfully registered!");
            alert.showAndWait();
            app.switchTo(event, "Login.fxml");
        }else if(aux.contains("Error:")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must fill all the textfields");
            alert.showAndWait();
        }
    }

    public void handleBack(ActionEvent event) throws IOException {
        app.switchTo(event, "Login.fxml");
    }
}

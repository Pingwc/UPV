package com.example.myissuesinterface;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 *
 * @author catmu
 */
public class HomeController implements Initializable {
    private StartApplication app = new StartApplication();
    private Label label;
    @FXML
    private Button LogOut;
    @FXML
    private Text Username;
    @FXML
    private Button ManageUsers;
    @FXML
    private Button ManageWorkspace;
    @FXML
    private Button ManageIssues;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonLogOut(ActionEvent event) throws Exception {
        app.sendToServer("logout");
        app.switchTo(event, "Login.fxml");
    }

    @FXML
    private void handleButtonManageUsers(ActionEvent event) throws IOException {
        String aux = app.sendToServer("listUsers");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Users List");
        alert.setHeaderText(aux);
        alert.showAndWait();
        //app.switchTo(event, "UserTable.fxml");
    }

    @FXML
    private void handleButtonManageWorkspace(ActionEvent event) throws IOException {
        app.switchTo(event, "MangeWorkspace.fxml");
    }

    @FXML
    private void handleButtonManageIssues(ActionEvent event) throws IOException {
        app.switchTo(event, "ManageIssues.fxml");
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.myissuesinterface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author catmu
 */
public class MangeWorkspaceController implements Initializable {
    @FXML public TextField UserJoin;
    @FXML public TextField userLeave;
    @FXML public Button List;
    @FXML public TextField workspaceLeave;
    @FXML public TextField workspaceJoin;
    @FXML private Label Information;
    private StartApplication app = new StartApplication();
    @FXML
    public Button JoinWorkspace1;
    @FXML public TextField AWName;
    @FXML public TextField Remove;
    @FXML public TextField Join;
    @FXML public TextField Leave;
    @FXML public TextField GetInfo;
    @FXML
    private Button AddWorkspace;
    @FXML
    private Button RemoveWorkspace;
    @FXML
    private Button SubmitWorkspace;
    @FXML
    private Button JoinWorkspace;
    @FXML
    private Button LeaveWorkspace;
    @FXML
    private Button Home;
    @FXML
    private Button ListWorkspace;
    @FXML
    private Text InfoWorkspace;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonAddWorkspace(ActionEvent event) throws IOException {
        String aux = "";
        aux = app.sendToServer("addWorkspace" + " " + AWName.getText()+ " ");
        if(aux.contains("successful")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(AWName.getText()+" Created successful");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must fill all the textfields" );
            alert.showAndWait();
        }
    }

    @FXML
    private void handleButtonRemoveWorkspace(ActionEvent event) throws IOException {
        String aux = "";
        aux = app.sendToServer("removeWorkspace" + " " + Remove.getText()+ " ");
        if(aux.contains("successful")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(Remove.getText()+" Removed successful");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must fill all the textfields" );
            alert.showAndWait();
        }
    }

    @FXML
    private void handleButtonSubmitGetInfo(ActionEvent event) throws IOException {
        String aux = "";
        aux = app.sendToServer("infoWorkspace" + " " + GetInfo.getText()+ " ");
        if(!aux.contains("Error")) {
            Information.setText(aux);
            Information.setStyle("-fx-text-fill: black");
        }else if(aux.contains("Error")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must fill all the textfields" );
            alert.showAndWait();
        }else if(aux.contains("java")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(GetInfo + " does not exists" );
            alert.showAndWait();
        }
    }

    @FXML
    private void handleButtonJoinWorkspace(ActionEvent event) throws IOException {
        String aux = "";
        aux = app.sendToServer("join" + " " + UserJoin.getText()+ " " + workspaceJoin.getText());
        if(aux.contains("successful")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(UserJoin.getText()+" Joined successful to " + workspaceJoin.getText());
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must fill all the textfields" );
            alert.showAndWait();
        }
    }

    @FXML
    private void handleButtonLeaveWorkspace(ActionEvent event) throws IOException {
        String aux = "";
        aux = app.sendToServer("leave" + " " + userLeave.getText()+ " " + workspaceLeave.getText());
        if(aux.contains("successful")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(userLeave.getText()+" Leaved successful to " + workspaceLeave.getText());
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must fill all the textfields" );
            alert.showAndWait();
        }
    }

    @FXML
    private void handleButtonHome(ActionEvent event) throws IOException {
        app.switchTo(event, "Home.fxml");
    }

    public void handleButtonGoToList(ActionEvent event) throws IOException {
        app.switchTo(event, "WorkspaceTable.fxml");
    }
    public void handleButtonList(ActionEvent event) throws IOException {
        String aux = app.sendToServer("listWorkspaces");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Workspaces List");
        alert.setHeaderText(aux);
        alert.showAndWait();
    }

    public void handleButtonBack(ActionEvent event) throws IOException{
        app.switchTo(event, "MangeWorkspace.fxml");
    }
}

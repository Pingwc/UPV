/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.myissuesinterface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author catmu
 */
public class IssuesController implements Initializable {
    @FXML public TextField WNAdd;
    @FXML public TextField INAccept;
    @FXML public TextField INAdd;
    @FXML public TextField INRemove;
    @FXML public TextField WNRemove;
    @FXML public TextField INAssign;
    @FXML public TextField INComment;
    @FXML public TextField CComment;
    @FXML public TextField INComplete;
    @FXML public TextField INRemoveIssue;
    @FXML public TextField INRejectIssue;
    @FXML public TextField INProposeIssue;
    @FXML public TextField INShowIssue;
    @FXML public Label IssueShow;
    @FXML public TextField NewIssueName;
    @FXML public DatePicker due;
    @FXML public TextField AssignTo;
    @FXML public Label ShowIssueName;
    @FXML public TextField IssueType;

    private StartApplication app = new StartApplication();

    private String listIssues [];
    @FXML
    private Button Home1;


    /**
     * Initializes the controller class.
     */


    @FXML
    public void handleButtonAddIssue(ActionEvent event) throws IOException{
        String aux = "";
        aux = app.sendToServer("addIssue "+ INAdd.getText()+ " " + WNAdd.getText());
        if(aux.contains("created successful")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(INAdd.getText() + " successfully created!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(aux);
            alert.showAndWait();
        }
    }

    @FXML //TODO
    public void handleButtonUpdateIssue(ActionEvent event) throws IOException{
        String aux = "";
        String date = due.getValue().toString().replace("-", "/");
        aux = app.sendToServer("updateIssue "+ INRemove.getText()+ " " + date + " " + NewIssueName.getText());
        if(aux.contains("successful")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(INRemove.getText() + " successfully updated!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(aux);
            alert.showAndWait();
        }
    }

    @FXML
    public void handleButtonHome(ActionEvent event) throws IOException {
        app.switchTo(event, "Home.fxml");
    }


    @FXML
    public void handleButtonListWorkspace(ActionEvent event) throws IOException{
        String aux = app.sendToServer("listIssues " + IssueType.getText()).replace(", ", " - ");
        /*String aux1 ="\n",aux2="\n";
        for(int i = 0; i<listIssues.length; i++) {
            if (i % 2 == 0){
                aux1 = aux1 + listIssues[i] + "\n";
            }
            else {
                aux2 = aux2 + listIssues[i] + "\n";
                //userObs.add(listIssues[i]);
            }
        }*/
        if(aux.contains("Error")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(IssueType.getText() + " list");
            alert.setHeaderText(aux);
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(IssueType.getText() + " list");
            alert.setHeaderText(aux);
            alert.showAndWait();
        }
    }

    @FXML
    public void handleButtonAcceptIssue(ActionEvent event) throws IOException {
        String aux = "";
        aux = app.sendToServer("acceptIssue "+ INAccept.getText());
        if(aux.contains("successful")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(INAccept.getText() + " successfully accepted!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(aux);
            alert.showAndWait();
        }
    }

    @FXML
    public void handleButtonAssign(ActionEvent event) throws IOException {
        String aux = "";
        aux = app.sendToServer("assignIssue "+ INAssign.getText() + " " + AssignTo.getText());
        if(aux.contains("successful")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(INAdd.getText() + " successfully assigned!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(aux);
            alert.showAndWait();
        }
    }

    @FXML
    public void handleButtonComment(ActionEvent event) throws IOException {
        String aux = "", comment = "\""+ CComment.getText() + "\"";
        System.out.println(comment);
        aux = app.sendToServer("commentIssue "+ INComment.getText() + " " + comment);
        if(aux.contains("been commented")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(INComment.getText() + " successfully commented!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(aux);
            alert.showAndWait();
        }
    }

    @FXML
    public void handleButtonComplete(ActionEvent event) throws IOException {
        String aux = "";
        aux = app.sendToServer("completeIssue "+ INComplete.getText());
        if(aux.contains("been completed")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(INComment.getText() + " successfully completed!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(aux);
            alert.showAndWait();
        }
    }

    @FXML
    public void handleButtonRemoveIssue(ActionEvent event) throws IOException {
        String aux = "";
        aux = app.sendToServer("removeIssue "+ INRemoveIssue.getText());
        if(aux.contains("been removed")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(INRemoveIssue.getText() + " successfully removed!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(aux);
            alert.showAndWait();
        }
    }

    @FXML
    public void handleButtonReject(ActionEvent event) throws IOException {
        String aux = "";
        aux = app.sendToServer("rejectIssue "+ INRejectIssue.getText());
        if(aux.contains("been rejected")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(INAccept.getText() + " successfully rejected!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(aux);
            alert.showAndWait();
        }
    }

    @FXML
    public void handleButtonPropose(ActionEvent event) throws IOException {
        String aux = "";
        aux = app.sendToServer("proposeIssue "+ INProposeIssue.getText());
        if(aux.contains("been proposed")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(INComment.getText() + " successfully proposed!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(aux);
            alert.showAndWait();
        }
    }

    @FXML
    public void handleButtonShow(ActionEvent event) throws IOException {
        String aux = app.sendToServer("showIssue " + INShowIssue.getText());
        if(aux.contains("I")){
            aux.replace(",", "   -   ");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(INShowIssue.getText() + " information");
            alert.setHeaderText(aux);
            alert.showAndWait();
        }else if(aux.contains("Please")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(INShowIssue.getText() + " information");
            alert.setHeaderText(aux);
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(INShowIssue.getText() + " information");
            alert.setHeaderText(aux);
            alert.showAndWait();
        }
    }

    public void handleButtonBack(ActionEvent event) throws IOException {
        app.switchTo(event, "ManageIssues.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

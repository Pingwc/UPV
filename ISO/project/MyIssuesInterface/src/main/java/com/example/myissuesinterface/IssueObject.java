package com.example.myissuesinterface;

import javafx.beans.property.SimpleStringProperty;

public class IssueObject extends IssuesController {
    private SimpleStringProperty issueID;
    private SimpleStringProperty user;

    public IssueObject(String issueID, String user){
        this.issueID = new SimpleStringProperty(issueID);
        this.user = new SimpleStringProperty(user);
    }

    public String getIssueID() {
        return issueID.get();
    }

    public String getUser() {
        return user.get();
    }
}

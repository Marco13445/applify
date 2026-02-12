package org.example.controllerAndMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Status;

import static model.JobApplication.convertStatusToString;
import static org.example.controllerAndMain.ControllerAppliedJobs.selectedJobApplication;

public class EditButtonController {

    @FXML
    Button okButton;

    @FXML
    Button exitButton;

    @FXML
    TextField newPostingNameField;

    @FXML
    TextField newCompanyNameField;

    @FXML
    TextField newPostingLinkField;

    @FXML
    ChoiceBox<String> applicationStatusChoiceBox;

    public static String newPostingName;
    public static String newCompanyName;
    public static String newPostingLink;
    public static String applicationStatus;

    public void initialize(){
        for( Status a : Status.values()) {
            applicationStatusChoiceBox.getItems().add(convertStatusToString(a));
        }
        applicationStatusChoiceBox.setValue(convertStatusToString(selectedJobApplication.getApplicationStatus()));
    }

    public void okButtonOnAction (ActionEvent e){

        newPostingName = newPostingNameField.getText();
        newCompanyName = newCompanyNameField.getText();
        newPostingLink = newPostingLinkField.getText();
        applicationStatus = applicationStatusChoiceBox.getValue();

        //Execution through method
        ApplifyMain.getService().updateJobApplication(selectedJobApplication, newPostingName,
                newCompanyName, newPostingLink, applicationStatus);

        //close secondary stage so that to turn back to primary stage
        //Here: to interrupt operation of making entries
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
    public void exitButtonOnAction (ActionEvent e){
        //close secondary stage so that to turn back to primary stage
        //Here: to interrupt operation of making entries
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}

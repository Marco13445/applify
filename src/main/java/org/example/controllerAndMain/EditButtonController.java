package org.example.controllerAndMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Status;

import java.time.LocalDate;

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

    private static String newPostingName;
    private static String newCompanyName;
    private static String newPostingLink;
    private static String applicationStatus;
    private static LocalDate newNextInterviewDate;
    private static String newNextInterviewLink;
    private static String newNextInterviewPlace;
    private static String newContactPersonFullName;
    private static String newNotes;



    public void initialize(){
        for( Status a : Status.values()) {
            applicationStatusChoiceBox.getItems().add(convertStatusToString(a));
        }
        applicationStatusChoiceBox.setValue(convertStatusToString(selectedJobApplication.getApplicationStatus()));
    }
    /**
     * stage for editbutton needs to be enhanced by new fields
     */
    public void okButtonOnAction (ActionEvent e){

        newPostingName = newPostingNameField.getText();
        newCompanyName = newCompanyNameField.getText();
        newPostingLink = newPostingLinkField.getText();
        applicationStatus = applicationStatusChoiceBox.getValue();
        newNextInterviewDate = null;
        newNextInterviewLink = "";
        newNextInterviewPlace = "";
        newContactPersonFullName = "";
        newNotes ="";


        //Execution through method
        ApplifyMain.getService().updateJobApplication(selectedJobApplication, newPostingName,
                newCompanyName, newPostingLink, applicationStatus,
                newNextInterviewDate, newNextInterviewLink, newNextInterviewPlace,
                newContactPersonFullName, newNotes);

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

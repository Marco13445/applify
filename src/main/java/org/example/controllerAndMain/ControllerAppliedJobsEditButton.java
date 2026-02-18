package org.example.controllerAndMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.JobApplication;

import java.time.LocalDate;

import static model.JobApplication.convertStatusToString;
import static org.example.controllerAndMain.ControllerAppliedJobsMainStage.selectedJobApplication;

public class ControllerAppliedJobsEditButton {

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

    @FXML
    DatePicker newNextInterviewDatePicker;

    @FXML
    TextField newNextInterviewLinkField;

    @FXML
    TextField newNextInterviewPlaceField;

    @FXML
    TextField newContactPersonField;

    @FXML
    TextField newNotesField;

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


        for( JobApplication.Status a : JobApplication.Status.values()) {
            applicationStatusChoiceBox.getItems().add(convertStatusToString(a));
        }

        try {
            applicationStatusChoiceBox.setValue(selectedJobApplication.getApplicationStatus());
        }
        catch(NullPointerException e){
            //System.out.println("Select a job application before pressing the 'edit'-button. ");
        }
    }
    /**
     * stage for editbutton needs to be enhanced by new fields
     */
    public void okButtonOnAction (ActionEvent e){

        newPostingName = newPostingNameField.getText();
        newCompanyName = newCompanyNameField.getText();
        newPostingLink = newPostingLinkField.getText();
        applicationStatus = applicationStatusChoiceBox.getValue();
        Object newNextInterviewDate;
        if(newNextInterviewDatePicker.getValue() == null){
            newNextInterviewDate = "";
        }else{
            newNextInterviewDate = newNextInterviewDatePicker.getValue();
        }
        newNextInterviewLink = newNextInterviewLinkField.getText();
        newNextInterviewPlace = newNextInterviewPlaceField.getText();
        newContactPersonFullName = newContactPersonField.getText();
        newNotes = newNotesField.getText();


        //Execution through method
        ApplifyMain.getService().updateJobApplication(selectedJobApplication, newPostingName,
                newCompanyName, newPostingLink, applicationStatus,
                newNextInterviewDate, newNextInterviewLink, newNextInterviewPlace,
                newContactPersonFullName, newNotes);

        //close secondary stage so that to turn back to primary stage
        //Here: to interrupt operation of making entries
        closeStage(e);
    }
    public void exitButtonOnAction (ActionEvent e){
        //close secondary stage so that to turn back to primary stage
        //Here: to interrupt operation of making entries
        closeStage(e);
    }
    private void closeStage(ActionEvent e){
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}

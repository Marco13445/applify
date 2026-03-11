package org.example.controllerAndMain.savedJobs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.JobApplication;
import org.example.controllerAndMain.ApplifyMain;

import java.io.IOException;
import java.time.LocalDate;

import static model.JobApplication.convertStatusToString;
import static org.example.controllerAndMain.savedJobs.ControllerMainStage.selectedSavedJob;


public class ControllerEditButton {

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
    public DatePicker newNextInterviewDatePicker;

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


    public void initialize() throws IOException {

        //Fills all fields with their current value before the user changes them eventually
        if (selectedSavedJob != null) {
            //Fill Textfields with current value
            newPostingNameField.setPromptText(selectedSavedJob.getPostingName());
            newCompanyNameField.setPromptText(selectedSavedJob.getCompany());
            newPostingLinkField.setPromptText(selectedSavedJob.getPostingLink().toString());
            newContactPersonField.setPromptText(selectedSavedJob.getContactPersonFullName());
            newNotesField.setPromptText(selectedSavedJob.getNotes());
        }
    }

    /**
     * stage for editbutton needs to be enhanced by new fields
     */
    public void okButtonOnAction(ActionEvent e) {

        newPostingName = newPostingNameField.getText();
        newCompanyName = newCompanyNameField.getText();
        newPostingLink = newPostingLinkField.getText();

        newContactPersonFullName = newContactPersonField.getText();
        newNotes = newNotesField.getText();


        //Execution through method
        ApplifyMain.getServiceSavedJobs().updateSavedJob(selectedSavedJob, newPostingName,
                newCompanyName, newPostingLink, newContactPersonFullName, newNotes);

        //close secondary stage so that to turn back to primary stage
        //Here: to interrupt operation of making entries
        closeStage(e);
    }

    public void exitButtonOnAction(ActionEvent e) {
        //close secondary stage so that to turn back to primary stage
        //Here: to interrupt operation of making entries
        closeStage(e);
    }

    private void closeStage(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}

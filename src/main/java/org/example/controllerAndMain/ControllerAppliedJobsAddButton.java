
package org.example.controllerAndMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.JobApplication;
import javafx.scene.control.TextArea;


import java.time.LocalDate;

//import static jdk.internal.net.http.common.Utils.close;

/**
 * Controller for the "Add Button" view.
 *
 * This controller is linked to {@code viewerAppliedJobsAddButton.fxml}.
 * It contains the logic for the stage that is opened
 * when the user clicks the "Add" button.
 */


public class ControllerAppliedJobsAddButton {

    @FXML
    private TextField AddPostingNameField;

    @FXML
    private TextField AddCompanyField;


    @FXML
    private DatePicker AddApplicationDatePicker;

    @FXML
    private TextField AddContactPersonField;

    @FXML
    private DatePicker AddNextInterviewDatePicker;

    @FXML
    private TextField AddNextInterviewLinkField;

    @FXML
    private TextField AddNextInterviewPlaceField;

    @FXML
    private TextArea AddNotesField;

    @FXML
    private TextField AddPostingLinkField;


    @FXML
    private Button okButton;

    @FXML
    private Button exitButton;

    public void okButtonOnAction(ActionEvent e) {
        //Fields to be entered by user
        String postingName = AddPostingNameField.getText();
        String companyName = AddCompanyField.getText();
        String postingLink = AddPostingLinkField.getText();
        LocalDate applicationDate = AddApplicationDatePicker.getValue();
        LocalDate nextInterviewDate = AddNextInterviewDatePicker.getValue();
        String nextInterviewLink = AddNextInterviewLinkField.getText();
        String nextInterviewPlace = AddNextInterviewPlaceField.getText();
        String contactPerson = AddContactPersonField.getText();
        String notes = AddNotesField.getText();

        //Execution through method
        ApplifyMain.getService().addJobApplication(new JobApplication(-1, postingName,
                companyName,postingLink,
                applicationDate,JobApplication.Status.WaitingForReply,
                nextInterviewDate, nextInterviewLink, nextInterviewPlace,
                contactPerson, notes));

        //close secondary stage so that to turn back to primary stage
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
    @FXML
    public void initialize() {

    }
}

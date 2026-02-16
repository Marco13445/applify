
package org.example.controllerAndMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.JobApplication;
//import model.Status;

import java.time.LocalDate;

//import static jdk.internal.net.http.common.Utils.close;

/**
 * Controller for the "Add Button" view.
 *
 * This controller is linked to {@code addButtonViewer.fxml}.
 * It contains the logic for the stage that is opened
 * when the user clicks the "Add" button.
 */


public class AddButtonController {

    @FXML
    private TextField postingNameField;

    @FXML
    private TextField companyField;

    @FXML
    private TextField postingLinkField;

    @FXML
    private DatePicker applicationDatePicker;

    @FXML
    private Button okButton;

    @FXML
    private Button exitButton;

    public void okButtonOnAction(ActionEvent e) {
        //Fields to be entered by user
        String postingName = postingNameField.getText();
        String companyName = companyField.getText();
        String postingLink = postingLinkField.getText();
        LocalDate applicationDate = applicationDatePicker.getValue();

        //Execution through method
        ApplifyMain.getService().addJobApplication(new JobApplication(-1, postingName,
                companyName,postingLink,
                applicationDate,JobApplication.Status.WaitingForReply));

        //close secondary stage so that to turn back to primary stage
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    public void exitButtonOnAction (ActionEvent e){
        //close secondary stage so that to turn back to primary stage
        //Here: to interrupt operation of making entries
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {

    }
}

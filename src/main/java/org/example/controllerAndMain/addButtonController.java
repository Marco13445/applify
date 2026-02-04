
package org.example.controllerAndMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.JobApplication;
import model.Status;

import java.time.LocalDate;

//import static jdk.internal.net.http.common.Utils.close;

/**
 * Controller for the "Add Button" view.
 *
 * This controller is linked to {@code addButtonViewer.fxml}.
 * It contains the logic for the stage that is opened
 * when the user clicks the "Add" button.
 */


public class addButtonController {

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

        String postingName = postingNameField.getText();
        String companyName = companyField.getText();
        String postingLink = postingLinkField.getText();
        LocalDate applicationDate = applicationDatePicker.getValue();


        ApplifyMain.getService().addJobApplication(new JobApplication(-1, postingName,
                companyName,postingLink,
                applicationDate,Status.WaitingForReply));
        /** ApplifyMain.getService().addJobApplication(new JobApplication(-1, postingNameField.getText(),
                companyField.getText(),postingLinkField.getText(),
                applicationDatePicker.getValue(),Status.WaitingForReply));
    */
    }

    public void exitButtonOnAction (ActionEvent e){
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {

    }
}

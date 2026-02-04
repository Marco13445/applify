
package org.example.controllerAndMain;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
    private TextField applicationDateField;

    @FXML
    public void initialize() {
        // optional
    }
}

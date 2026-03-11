package org.example.controllerAndMain.savedJobs;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerEditButtonAlert {

    @FXML
    private Button okButton;



    public void okButtonOnAction(javafx.event.ActionEvent actionEvent) {
        closeStage(actionEvent);
    }
    private void closeStage(javafx.event.ActionEvent e){
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}

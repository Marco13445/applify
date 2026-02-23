package org.example.controllerAndMain;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class ControllerAppliedJobsEditButtonAlert {

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

package org.example.controllerAndMain.appliedJobs;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ControllerAppliedJobsDeleteButtonAlert {
    public void okButtonOnAction(ActionEvent actionEvent) {
        closeStage(actionEvent);
    }
    private void closeStage(javafx.event.ActionEvent e){
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}

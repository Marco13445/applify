package org.example.controllerAndMain.savedJobs;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ControllerDeleteButtonAlert {
    public void okButtonOnAction(ActionEvent actionEvent) {
        closeStage(actionEvent);
    }
    private void closeStage(ActionEvent e){
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}

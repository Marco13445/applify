package org.example.controllerAndMain;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 *
 * This is the controller class. It defines WHAT HAPPENS when the user activates UI.
 * HOW the UI has to LOOK like is defined in the FXML-file
 *
 */
public class HelloController {
    @FXML
    private Label buttonPressedText;

    @FXML
    protected void onHelloABCButtonClick() {
        buttonPressedText.setText("Hi, I'm get acquainted with JavaFX!");
    }

    @FXML
    protected void onNewButtonClick(){
        buttonPressedText.setText("The new button changes the text :D. ");
    }

    @FXML
    private ListView list;

}
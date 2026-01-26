package org.example.controllerAndMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.JobApplication;

public class applifyController {

    @FXML
    private Label filterLabel;

    @FXML
    private ChoiceBox criteriaDropDown;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<JobApplication> listView;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    public void criteriaDropDownOnAction(ActionEvent event){

    }
    public void searchButtonOnAction(ActionEvent event){

    }
    public void addButtonOnAction(ActionEvent event){

    }
    public void editButtonOnAction(ActionEvent event){

    }
    public void deleteButtonOnAction(ActionEvent event){
        deleteButton.setText("DELETE");
    }
    public void initialise(){
       // list.setItems(applicationList);
    }

}

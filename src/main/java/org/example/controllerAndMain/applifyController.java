package org.example.controllerAndMain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.JobApplication;
import model.Status;
import service.ApplicationService;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class applifyController {

    //Field
    ArrayList<JobApplication> applicationList;

    @FXML
    private Label filterLabel;

    @FXML
    private ChoiceBox criteriaDropDown;

    @FXML
    private Button searchButton;

    @FXML
    private TableView <JobApplication> table = new TableView<>();

    @FXML
    private TableColumn <JobApplication, Integer> table_id;

    @FXML
    private TableColumn <JobApplication, String> table_Posting_Name;
    @FXML
    private TableColumn <JobApplication, String> table_Company_Name;
    @FXML
    private TableColumn <JobApplication, String> table_Posting_Link;
    @FXML
    private TableColumn <JobApplication, LocalDate> table_Application_Date;
    @FXML
    private TableColumn <JobApplication, Status> table_Application_Status;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    public void criteriaDropDownOnAction(ActionEvent event){

    }
    public void searchButtonOnAction(ActionEvent event){

        System.out.println("Search button clicked");
    }
    public void addButtonOnAction(ActionEvent event){
        
        //ApplifyMain.getService().addJobApplication();
    }
    public void editButtonOnAction(ActionEvent event){

    }
    public void deleteButtonOnAction(ActionEvent event){
       // ApplifyMain.getService().removeJobApplication();
    }
    public void initialize(){
        ApplifyMain.getService().readJobApplicationsFromDatabase();
        applicationList = (ArrayList<JobApplication>) ApplifyMain.getService().getApplicationList();

        //connect id_column with field id
        table_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_Posting_Name.setCellValueFactory(new PropertyValueFactory<>("postingName"));
        table_Company_Name.setCellValueFactory(new PropertyValueFactory<>("company"));
        table_Posting_Link.setCellValueFactory(new PropertyValueFactory<>("postingLink"));
        table_Application_Date.setCellValueFactory(new PropertyValueFactory<>("applicationDate"));
        table_Application_Status.setCellValueFactory(new PropertyValueFactory<>("applicationStatus"));


        //Fill list with data
        ObservableList<JobApplication> observableList = FXCollections.observableList(applicationList);

        //view in table
        table.setItems(observableList);
    }


}

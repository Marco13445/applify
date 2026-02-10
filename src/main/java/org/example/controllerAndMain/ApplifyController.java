package org.example.controllerAndMain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.JobApplication;
import model.Status;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static model.JobApplication.convertStatusToString;


public class ApplifyController {

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

    public static JobApplication selectedJobApplication;

    private int selectedSearchCriterion;

    public void criteriaDropDownOnAction(ActionEvent event){

    }

    public void searchButtonOnAction(ActionEvent event){
        //define value of search criterion based on selected criterion
                if (criteriaDropDown.getSelectionModel().getSelectedItem().equals("Invitation")){
                    selectedSearchCriterion = 1;
                }else if(criteriaDropDown.getSelectionModel().getSelectedItem().equals("Last three weeks")){
                    selectedSearchCriterion = 2;
                }else {
                    selectedSearchCriterion = 0;
                };

        ArrayList<JobApplication> searchList = ApplifyMain.getService().searchJobApplication(selectedSearchCriterion);


        //connect id_column with field id
        table_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_Posting_Name.setCellValueFactory(new PropertyValueFactory<>("postingName"));
        table_Company_Name.setCellValueFactory(new PropertyValueFactory<>("company"));
        table_Posting_Link.setCellValueFactory(new PropertyValueFactory<>("postingLink"));
        table_Application_Date.setCellValueFactory(new PropertyValueFactory<>("applicationDate"));
        table_Application_Status.setCellValueFactory(new PropertyValueFactory<>("applicationStatus"));

        //Fill list with data from search list this time, not from application list
        ObservableList<JobApplication> observableList = FXCollections.observableList(searchList);

        //view in table
        table.setItems(observableList);


    }
    public void addButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplifyMain.class.getResource("/org/example/applify/addButtonViewer.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Add information about applied Job");
        secondaryStage.setScene(scene);

        //block primary stage while secondary stage is open
        secondaryStage.initOwner((Stage) ((Button) event.getSource()).getScene().getWindow()); // block the primary stage
        secondaryStage.initModality(Modality.WINDOW_MODAL); // modal to owner
        secondaryStage.showAndWait();                    // 🔒 blocks the primary stage

        refreshTableView();


    }

    public void editButtonOnAction(ActionEvent event) throws IOException{
        //JobApplication should be selected before new stage opens
        selectedJobApplication = table.getSelectionModel().getSelectedItem();

        FXMLLoader fxmlLoader = new FXMLLoader(ApplifyMain.class.getResource("/org/example/applify/editButtonViewer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Add information about applied Job to be edited");
        secondaryStage.setScene(scene);

        //block primary stage while secondary stage is open
        secondaryStage.initOwner((Stage) ((Button) event.getSource()).getScene().getWindow()); // block the primary stage
        secondaryStage.initModality(Modality.WINDOW_MODAL); // modal to owner
        if(selectedJobApplication!=null){
            secondaryStage.showAndWait();
            refreshTableView();
        }else {
            System.out.println("A job application must be selected before the edit Button is clicked. ");
        }
    }
    public void deleteButtonOnAction(ActionEvent event){
        selectedJobApplication = table.getSelectionModel().getSelectedItem();
        ApplifyMain.getService().removeJobApplication(selectedJobApplication);
        refreshTableView();
    }
    public void initialize(){
        //initialise criteria dropdowns
        criteriaDropDown.setValue("No selection");
        criteriaDropDown.getItems().add("No selection");
        criteriaDropDown.getItems().add("Invitation");
        criteriaDropDown.getItems().add("Last three weeks");

        refreshTableView();
    }

    private void refreshTableView() {
        //search without criterion
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

        criteriaDropDown.setValue("No selection");
    }



}

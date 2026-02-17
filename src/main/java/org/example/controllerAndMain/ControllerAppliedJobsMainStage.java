package org.example.controllerAndMain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.JobApplication;
import model.JobApplication.Status;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


public class ControllerAppliedJobsMainStage {

    //Field
    ArrayList<JobApplication> applicationList;

    @FXML
    private Label filterLabel;

    @FXML
    private ChoiceBox criteriaDropDown;

    @FXML
    private Button searchButton;

    @FXML
    private TableView <JobApplication> tableAppliedJobs = new TableView<>();

    @FXML
    private TableColumn <JobApplication, Integer> column1;

    @FXML
    private TableColumn <JobApplication, String> column2;
    @FXML
    private TableColumn <JobApplication, String> column3;
    @FXML
    private TableColumn <JobApplication, String> column4;
    @FXML
    private TableColumn <JobApplication, LocalDate> column5;
    @FXML
    private TableColumn <JobApplication, JobApplication.Status> column6;
    @FXML
    private TableColumn <JobApplication, JobApplication.Status> column7;
    @FXML
    private TableColumn <JobApplication, JobApplication.Status> column8;
    @FXML
    private TableColumn <JobApplication, JobApplication.Status> column9;
    @FXML
    private TableColumn <JobApplication, JobApplication.Status> column10;
    @FXML
    private TableColumn <JobApplication, JobApplication.Status> column11;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    public static JobApplication selectedJobApplication;

    private int selectedSearchCriterion;

    @FXML
    private void filter(Event e){
        String selected = (String) criteriaDropDown.getSelectionModel().getSelectedItem();

        if ("invitation".equals(selected)) {
            selectedSearchCriterion = 1;
        } else if ("last three weeks".equals(selected)) {
            selectedSearchCriterion = 2;
        } else {
            selectedSearchCriterion = 0;
        }
        refreshTableView();
    }
    private void refreshTableView() {
        //search without criterion
        ApplifyMain.getService().readJobApplicationsFromDatabase();

        ArrayList<JobApplication> searchList = ApplifyMain.getService().searchJobApplication(selectedSearchCriterion);

        //connect id_column with field id
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("postingName"));
        column3.setCellValueFactory(new PropertyValueFactory<>("company"));
        column4.setCellValueFactory(new PropertyValueFactory<>("postingLink"));
        column5.setCellValueFactory(new PropertyValueFactory<>("applicationDate"));
        column6.setCellValueFactory(new PropertyValueFactory<>("applicationStatus"));
        column7.setCellValueFactory(new PropertyValueFactory<>("nextInterviewDate"));
        column8.setCellValueFactory(new PropertyValueFactory<>("nextInterviewLink"));
        column9.setCellValueFactory(new PropertyValueFactory<>("nextInterviewPlace"));
        column10.setCellValueFactory(new PropertyValueFactory<>("contactPersonFullName"));
        column11.setCellValueFactory(new PropertyValueFactory<>("notes"));

        //Fill list with data from search list this time, not from application list
        ObservableList<JobApplication> observableList = FXCollections.observableList(searchList);

        //view in table
        tableAppliedJobs.setItems(observableList);
    }


    public void addButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplifyMain.class.getResource("/org/example/applify/fxml_files/20260211_modernStyle/viewerAppliedJobsAddButton.fxml")
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
        selectedJobApplication = tableAppliedJobs.getSelectionModel().getSelectedItem();

        FXMLLoader fxmlLoader = new FXMLLoader(ApplifyMain.class.getResource("/org/example/applify/fxml_files/20260211_modernStyle/viewerAppliedJobsEditButton.fxml"));
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
        selectedJobApplication = tableAppliedJobs.getSelectionModel().getSelectedItem();
        ApplifyMain.getService().removeJobApplication(selectedJobApplication);
        refreshTableView();
    }
    public void initialize(){
        //Fill choice box with options
        criteriaDropDown.getItems().add("no filter");
        criteriaDropDown.getItems().add("invitation");
        criteriaDropDown.getItems().add("last three weeks");
        //initialise criteria dropdowns
        criteriaDropDown.setValue("no filter");
        refreshTableView();
        criteriaDropDown.setOnAction((actionEvent -> filter (actionEvent) ));


    }







}

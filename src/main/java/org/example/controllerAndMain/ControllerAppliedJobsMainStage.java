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

import java.awt.event.KeyEvent;
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
    private TableView<JobApplication> tableAppliedJobs = new TableView<>();

    @FXML
    private TableColumn<JobApplication, Integer> column1;

    @FXML
    private TableColumn<JobApplication, String> column2;
    @FXML
    private TableColumn<JobApplication, String> column3;
    @FXML
    private TableColumn<JobApplication, String> column4;
    @FXML
    private TableColumn<JobApplication, LocalDate> column5;
    @FXML
    private TableColumn<JobApplication, JobApplication.Status> column6;
    @FXML
    private TableColumn<JobApplication, JobApplication.Status> column7;
    @FXML
    private TableColumn<JobApplication, JobApplication.Status> column8;
    @FXML
    private TableColumn<JobApplication, JobApplication.Status> column9;
    @FXML
    private TableColumn<JobApplication, JobApplication.Status> column10;
    @FXML
    private TableColumn<JobApplication, JobApplication.Status> column11;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField searchfield;

    public static JobApplication selectedJobApplication;
    public int filterNumber;
    public String searchword = "";


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

        refreshTableView(filterNumber, searchword);
    }

    public void editButtonOnAction(ActionEvent event) throws IOException {
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
        if (selectedJobApplication != null) {
            secondaryStage.showAndWait();
            refreshTableView(filterNumber, searchword);
        } else {
            System.out.println("A job application must be selected before the edit Button is clicked. ");
        }
    }

    public void deleteButtonOnAction(ActionEvent event) {
        selectedJobApplication = tableAppliedJobs.getSelectionModel().getSelectedItem();
        ApplifyMain.getService().removeJobApplication(selectedJobApplication);
        refreshTableView(filterNumber, searchword);
    }

    public void initialize() {
        //Fill choice box with options
        criteriaDropDown.getItems().add("no filter");
        criteriaDropDown.getItems().add("invitation");
        criteriaDropDown.getItems().add("last three weeks");
        //initialise criteria dropdowns
        criteriaDropDown.setValue("no filter");
        refreshTableView(0, ""); //default table should be generated based on the full list, i.e. no filter, no search word
        criteriaDropDown.setOnAction((actionEvent -> defineFilterNumber(actionEvent))); //if any option is chose on the dropdownmenu,
        //an action is triggered which again triggers the method 'defineFilterNumber' which again refreshes the TableView

    }

    @FXML
    private void defineFilterNumber(Event e) {
        String selected = (String) criteriaDropDown.getSelectionModel().getSelectedItem();
        if ("invitation".equals(selected)) {
            filterNumber = 1;
        } else if ("last three weeks".equals(selected)) {
            filterNumber = 2;
        } else {
            filterNumber = 0;
        }
        refreshTableView(filterNumber, searchword);
    }

    public void getSearchWord(javafx.scene.input.KeyEvent keyEvent) {
        searchword = searchfield.getText();
        System.out.println("sout in method 'getSearchWord(KeyEvent)'" + searchword);
        refreshTableView(filterNumber, searchword);
    }

    //show table based on created list from search and/or filter and/or full list
    private void showTable(ArrayList<JobApplication> list) {
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
        ObservableList<JobApplication> observableList = FXCollections.observableList(list);
        //view in table
        tableAppliedJobs.setItems(observableList);

    }

    private void refreshTableView(int filternumber, String searchword) {
        ArrayList<JobApplication> list = new ArrayList<>();

        try {
            ApplifyMain.getService().readJobApplicationsFromDatabase(); //reads database and fills the (full) applicationlist
            ArrayList<JobApplication> fullList = (ArrayList<JobApplication>) ApplifyMain.getService().getApplicationList(); //gets the full applicationlist
            ArrayList<JobApplication> filterList = ApplifyMain.getService().createFilterList(filternumber); //creates a list based on the filter criterion
            ArrayList<JobApplication> searchList = ApplifyMain.getService().createSearchList(searchword); //creates a list based on the typed searchword
            ArrayList<JobApplication> jointList = ApplifyMain.getService().createJointList(filterList, searchList); //creates a jointlist out of the filterlist and searchlist
            //in case both functions are activated
            if (filternumber == 0 && searchword == "") {
                list = fullList;
            } else if (filternumber != 0 && searchword == "") {
                list = filterList;
            } else if (filternumber == 0 && searchword != "") {
                list = searchList;
            } else if (filternumber != 0 && searchword != "") {
                list = jointList;
            }
            //based on the list as parameter the filter/search/full - list is shown
            showTable(list);
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
    }

}

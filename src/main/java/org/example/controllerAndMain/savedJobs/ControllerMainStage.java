package org.example.controllerAndMain.savedJobs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.JobApplication;
import org.example.controllerAndMain.ApplifyMain;

import java.io.IOException;
import java.util.ArrayList;


public class ControllerMainStage {

    //Field
    ArrayList<JobApplication> applicationList;

    @FXML
    private Label filterLabel;

    @FXML
    private ChoiceBox criteriaDropDown;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<JobApplication> tableSavedJobs = new TableView<>();

    @FXML
    private TableColumn<JobApplication, Integer> column1;

    @FXML
    private TableColumn<JobApplication, String> column2;
    @FXML
    private TableColumn<JobApplication, String> column3;
    @FXML
    private TableColumn<JobApplication, String> column4;

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
    private Button btnSavedJobsApplJobs;

    @FXML
    private Button btnAppliedJobsStat;

    @FXML
    private Button btnSavedJobsStat;


    @FXML
    private TextField searchfield;
    public static JobApplication selectedSavedJob;
    public int filterNumber;
    public String searchword = "";

    public void hyperlinkPressed(ActionEvent event){
        System.out.println("hyperlink");
    }


    public void addButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplifyMain.class.getResource("/org/example/applify/fxml_files/modernStyle/savedJobs/viewerAddButton.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Add information about saved Job");
        secondaryStage.setScene(scene);

        //block primary stage while secondary stage is open
        secondaryStage.initOwner((Stage) ((Button) event.getSource()).getScene().getWindow()); // block the primary stage
        secondaryStage.initModality(Modality.WINDOW_MODAL); // modal to owner
        secondaryStage.showAndWait();                    // blocks the primary stage

        refreshTableView(searchword);

    }

    public void editButtonOnAction(ActionEvent event) throws IOException {
        //JobApplication should be selected before new stage opens
        selectedSavedJob = tableSavedJobs.getSelectionModel().getSelectedItem();

        FXMLLoader fxmlLoader = new FXMLLoader(ApplifyMain.class.getResource(
                "/org/example/applify/fxml_files/modernStyle/savedJobs/viewerEditButton.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Add information about saved job to be edited");
        secondaryStage.setScene(scene);

        //block primary stage while secondary stage is open
        secondaryStage.initOwner((Stage) ((Button) event.getSource()).getScene().getWindow()); // block the primary stage
        secondaryStage.initModality(Modality.WINDOW_MODAL); // modal to owner

        //If any job application has been selected
        if (selectedSavedJob != null) {
            System.out.println("ID of selected saved job: " + selectedSavedJob.getId());
            secondaryStage.showAndWait();
            refreshTableView(searchword);
        } else {
            //If NOT any job application has been selected.
            fxmlLoader = new FXMLLoader(ApplifyMain.class.getResource(
                    "/org/example/applify/fxml_files/modernStyle/savedJobs/viewerEditButtonAlert.fxml"));
            scene = new Scene(fxmlLoader.load());
            Stage editAlertStage = new Stage();
            editAlertStage.setTitle("Advise");
            editAlertStage.setScene(scene);

            //block primary stage while secondary stage is open
            editAlertStage.initOwner((Stage) ((Button) event.getSource()).getScene().getWindow()); // block the primary stage
            editAlertStage.initModality(Modality.WINDOW_MODAL); // modal to owner

            editAlertStage.showAndWait();

            System.out.println("A saved application must be selected before the edit Button is clicked. ");

        }
    }

    public void deleteButtonOnAction(ActionEvent event) throws IOException {
        selectedSavedJob = tableSavedJobs.getSelectionModel().getSelectedItem();
        //If any job application has been selected
        if (selectedSavedJob != null) {
            ApplifyMain.getServiceSavedJobs().removeJob(selectedSavedJob);
            refreshTableView(searchword);
        }else{
            //If NOT any job application has been selected.
            FXMLLoader fxmlLoader = new FXMLLoader(ApplifyMain.class.getResource(
                    "/org/example/applify/fxml_files/modernStyle/savedJobs/viewerDeleteButtonAlert.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage editAlertStage = new Stage();
            editAlertStage.setTitle("Advise");
            editAlertStage.setScene(scene);

            //block primary stage while secondary stage is open
            editAlertStage.initOwner((Stage) ((Button) event.getSource()).getScene().getWindow()); // block the primary stage
            editAlertStage.initModality(Modality.WINDOW_MODAL); // modal to owner

            editAlertStage.showAndWait();

            System.out.println("A saved application must be selected before the delete Button is clicked. ");
        }
    }

    public void initialize() {

        refreshTableView( ""); //default table should be generated based on the full list, i.e. no filter, no search word

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
        refreshTableView(searchword);
    }

    public void getSearchWord(javafx.scene.input.KeyEvent keyEvent) {
        searchword = searchfield.getText();
        refreshTableView(searchword);
    }

    //show table based on created list from search and/or filter and/or full list
    private void showTable(ArrayList<JobApplication> list) {
        //connect id_column with field id
        //column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("postingName"));
        column3.setCellValueFactory(new PropertyValueFactory<>("company"));
        column4.setCellValueFactory(new PropertyValueFactory<>("postingLink"));
        column10.setCellValueFactory(new PropertyValueFactory<>("contactPersonFullName"));
        column11.setCellValueFactory(new PropertyValueFactory<>("notes"));

        column4.setCellFactory(col -> new TableCell<>() {
            private final Hyperlink link = new Hyperlink();

            {
                link.setOnAction(e -> {
                    String url = getItem();
                    if (url != null && !url.isEmpty()) {
                        try {
                            java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                link.setMaxWidth(Double.MAX_VALUE);
                link.setAlignment(Pos.CENTER_LEFT);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.isEmpty()) {
                    setGraphic(null);
                } else {
                    link.setText(item);
                    setGraphic(link);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setAlignment(Pos.CENTER_LEFT);
                }
            }
        });



        //Fill list with data from search list this time, not from application list
        ObservableList<JobApplication> observableList = FXCollections.observableList(list);
        //view in table
        tableSavedJobs.setItems(observableList);
        //fills the whole predefined space in the stage
        tableSavedJobs.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    private void refreshTableView(String searchword) {
        ArrayList<JobApplication> list = new ArrayList<>();

        try {
            ApplifyMain.getServiceSavedJobs().readJobsFromDatabase(); //reads database and fills the (full) applicationlist
            ArrayList<JobApplication> fullList = (ArrayList<JobApplication>) ApplifyMain.getServiceSavedJobs().getSavedJobsList(); //gets the full applicationlist
            ArrayList<JobApplication> searchList = ApplifyMain.getServiceSavedJobs().createSearchList(searchword); //creates a list based on the typed searchword
            //in case both functions are activated
            if (searchword == "") {
                list = fullList;
            } else if (searchword != "") {
                list = searchList;
            }
            //based on the list as parameter the filter/search/full - list is shown
            showTable(list);
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
    }

    @FXML
    private void btnSavedJobsStat(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/applify/fxml_files/modernStyle/statistics/viewerStatistics.fxml")
        );

        Parent root = loader.load();


        Stage stage = (Stage) btnSavedJobsStat.getScene().getWindow();
        stage.setTitle("Applify - Statistics");
        stage.setScene(new Scene(root, 1500, 700));
    }

    @FXML
    private void btnSavedJobsApplJobs (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/applify/fxml_files/modernStyle/appliedJobs/viewerAppliedJobs.fxml")
        );

        Parent root = loader.load();

        Stage stage = (Stage) btnSavedJobsApplJobs.getScene().getWindow();
        stage.setTitle("Applify - Applied Jobs");
        stage.setScene(new Scene(root, 1500, 700));
    }

}

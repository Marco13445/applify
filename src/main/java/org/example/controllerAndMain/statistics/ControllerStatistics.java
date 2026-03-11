package org.example.controllerAndMain.statistics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.JobApplication;
import org.example.controllerAndMain.ApplifyMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ControllerStatistics {


    @FXML
    private Button btnStatisticsAppliedJobs;

    @FXML
    private Button btnStatisticsSavedJobs;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private ObservableList<String> categories = FXCollections.observableArrayList();

    @FXML
    private ValueAxis yAxis;

    @FXML
    private BarChart<String, Number> barChart;

    double numberOfApplications = 0;
    double offer = 0;
    double withdrawn = 0;
    double rejection = 0;
    double invitation =0;

    Number invitationPercentage, offerPercentage, withdrawnPercentage, rejectionPercentage = 0;;



    public void initialize(){
        //Get an array with the categories for the xAxis
        String[] categoriesList  = new String[]{"Percentage of invitations [%]", "Percentage of rejections [%]",
                "Percentage of withdrawn applications [%]", "Percentage of offers [%]"};
        //Convert the array to a list and add it to an ObservableList of categories.
        categories.addAll(Arrays.asList(categoriesList));
        //Assign the categories to the horizontal x-Axis.
        xAxis.setCategories(categories);

        xAxis.setLabel("Criterion");
        yAxis.setLabel("Percentage [%]");

        XYChart.Series <String, Number> series1 = new XYChart.Series<>();

        count();

        //series1.getData().add(new XYChart.Data<>("Total number of applied Jobs [#]", 10));
        series1.getData().add(new XYChart.Data<>("Percentage of invitations [%]", invitationPercentage));
        series1.getData().add(new XYChart.Data<>("Percentage of rejections [%]", rejectionPercentage));
        series1.getData().add(new XYChart.Data<>("Percentage of withdrawn applications [%]", withdrawnPercentage));
        series1.getData().add(new XYChart.Data<>("Percentage of offers [%]", offerPercentage));

        barChart.getData().add(series1);
    }

    private void count(){
        ApplifyMain.getService().readJobApplicationsFromDatabase(); //reads database and fills the (full) applicationlist
        ArrayList<JobApplication> fullList = (ArrayList<JobApplication>) ApplifyMain.getService().getApplicationList(); //gets the full applicationlist


        //counting
        for (JobApplication application : fullList){
            if(application.getApplicationStatus().equalsIgnoreCase("Offer")){
                offer++;
            }
            else if(application.getApplicationStatus().equalsIgnoreCase("Withdrawn")){
                withdrawn++;
            }
            else if(application.getApplicationStatus().equalsIgnoreCase("Rejected")){
                rejection++;
            }
            else if(application.getApplicationStatus().equalsIgnoreCase("Invitation")){
                invitation++;
            }
            numberOfApplications++;
        }
        //calculating percentages
        invitationPercentage = Math.round ((invitation/numberOfApplications)*100);
        rejectionPercentage = Math.round((rejection/numberOfApplications)*100);
        withdrawnPercentage = Math.round  ((withdrawn/numberOfApplications)*100);
        offerPercentage = Math.round(  (offer/numberOfApplications)*100);

        System.out.println("Offer: "+offerPercentage);
        System.out.println("Withdrawn: "+ withdrawnPercentage);
        System.out.println("Rejected: "+  rejectionPercentage);
        System.out.println("Invitation: "+invitationPercentage);


    }

    @FXML
    private void btnStatisticsAppliedJobsPressed (ActionEvent e) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/applify/fxml_files/modernStyle/appliedJobs/viewerAppliedJobs.fxml")
        );

        Parent root = loader.load();

        Stage stage = (Stage) btnStatisticsAppliedJobs.getScene().getWindow();
        stage.setTitle("Applify - Applied Jobs");
        stage.setScene(new Scene(root, 1500, 700));
    }

    @FXML
    private void btnStatisticsSavedJobsPressed (ActionEvent e) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/applify/fxml_files/modernStyle/savedJobs/viewerSavedJobs.fxml")
        );

        Parent root = loader.load();

        Stage stage = (Stage) btnStatisticsSavedJobs.getScene().getWindow();
        stage.setTitle("Applify - Applied Jobs");
        stage.setScene(new Scene(root, 1500, 700));

    }
}

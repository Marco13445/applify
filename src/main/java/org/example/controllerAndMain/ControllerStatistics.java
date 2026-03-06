package org.example.controllerAndMain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class ControllerStatistics {


    @FXML
    private Button btnStatisticsAppliedJobs;;



    @FXML
    private CategoryAxis xAxis;

    @FXML
    private ObservableList<String> categories = FXCollections.observableArrayList();

    @FXML
    private ValueAxis yAxis;

    @FXML
    private BarChart<String, Number> barChart;


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

        //series1.getData().add(new XYChart.Data<>("Total number of applied Jobs [#]", 10));
        series1.getData().add(new XYChart.Data<>("Percentage of invitations [%]", 10));
        series1.getData().add(new XYChart.Data<>("Percentage of rejections [%]", 10));
        series1.getData().add(new XYChart.Data<>("Percentage of withdrawn applications [%]", 10));
        series1.getData().add(new XYChart.Data<>("Percentage of offers [%]", 10));

        barChart.getData().add(series1);
    }

    @FXML
    private void btnStatisticsAppliedJobsPressed (ActionEvent e) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/applify/fxml_files/20260211_modernStyle/viewerAppliedJobs.fxml")
        );

        Parent root = loader.load();

        Stage stage = (Stage) btnStatisticsAppliedJobs.getScene().getWindow();
        stage.setTitle("Applify - Applied Jobs");
        stage.setScene(new Scene(root));
    }
}

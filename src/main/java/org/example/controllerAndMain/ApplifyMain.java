package org.example.controllerAndMain;

import database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.JobApplication;
import service.ApplicationService;

import java.io.IOException;
import java.time.LocalDate;


public class ApplifyMain extends Application {

    //Fields
    private static DatabaseHandler databaseHandler = new DatabaseHandler();
    private static ApplicationService service = new ApplicationService(databaseHandler);


    public static ApplicationService getService() {
        return service;
    }

    public static void setService(ApplicationService service) {
        ApplifyMain.service = service;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/applify/fxml_files/20260211_modernStyle/viewerAppliedJobs.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 800);
        primaryStage.setTitle("Applify Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
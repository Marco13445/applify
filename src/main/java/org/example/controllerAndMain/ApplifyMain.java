package org.example.controllerAndMain;

import database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import service.ApplicationService;
import java.io.IOException;

import static javafx.application.Application.launch;


public class ApplifyMain extends Application {

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

            //default fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/applify/fxml_files/20260211_modernStyle/viewerAppliedJobs.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1500, 700);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/org/example/applify/Icon/20260302_Logo_minimalistisch.png")));
            primaryStage.setTitle("Applify - Applied Jobs");
            primaryStage.setScene(scene);
            primaryStage.show();
        }


    public static void main(String[] args) {
        launch();
    }
}
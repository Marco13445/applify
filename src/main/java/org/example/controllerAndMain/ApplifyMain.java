package org.example.controllerAndMain;

import database.appliedJobs.DatabaseHandlerAppliedJobs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import service.appliedJobs.ApplicationService;
import java.io.IOException;

import static javafx.application.Application.launch;


public class ApplifyMain extends Application {

    private static DatabaseHandlerAppliedJobs databaseHandlerAppliedJobs = new DatabaseHandlerAppliedJobs();
    private static ApplicationService service = new ApplicationService(databaseHandlerAppliedJobs);
    public static ApplicationService getService() {
        return service;
    }
    public static void setService(ApplicationService service) {
        ApplifyMain.service = service;
    }

    private static database.savedJobs.DatabaseHandler databaseHandlerSavedJobs = new database.savedJobs.DatabaseHandler();
    private static service.savedJobs.ApplicationService serviceSavedJobs = new service.savedJobs.ApplicationService(databaseHandlerSavedJobs);
    public static service.savedJobs.ApplicationService getServiceSavedJobs(){
        return serviceSavedJobs;
    }
    public static void setServiceSavedJobs(service.savedJobs.ApplicationService serviceSavedJobs){
        ApplifyMain.serviceSavedJobs= serviceSavedJobs;
    }



    @Override
    public void start(Stage primaryStage) throws IOException {

            //default fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/applify/fxml_files/modernStyle/appliedJobs/viewerAppliedJobs.fxml"));
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
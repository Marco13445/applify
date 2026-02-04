package org.example.controllerAndMain;

import database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.JobApplication;
import model.Status;
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
        FXMLLoader fxmlLoader = new FXMLLoader(ApplifyMain.class.getResource("/org/example/applify/applifyViewer.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 900, 800);
        primaryStage.setTitle("Applify Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();

/**
        BorderPane root = new BorderPane();
        ListView <String> list = new ListView<>();
        list.getItems().add("number 1");
        list.getItems().add("number 2");
        list.getItems().add("number 3");
        root.setCenter(list);

        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
*/
        System.out.println("test 0-1");
    }

    public static void main(String[] args) {
        launch();
        /** databaseHandler.insertIntoDatabase(new JobApplication(-10,"Intern", "VARTA", "www.company.com", LocalDate.of(2026,01,26),Status.WaitingForReply));
        databaseHandler.deleteFromDatabase(new JobApplication(-20, "Intern", "VARTA", "www.company.com", LocalDate.of(2026,01,26),Status.WaitingForReply));
        databaseHandler.updateDatabase(new JobApplication(-30,"Intern", "VARTA", "www.company.com", LocalDate.of(2026,01,26),Status.WaitingForReply), "hired", "BOSCH");
        */
       //new ApplicationService(databaseHandler).updateJobApplication(new JobApplication(-100,"VARTA", "Intern", "www.company.com", LocalDate.of(2026,01,31),Status.WaitingForReply), "Trainee","STW BO");
        System.out.println("Test 1"); // magic happens in the launch method
    }
}
package org.example.controllerAndMain;

import database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.JobApplication;
import model.Status;
import service.ApplicationService;

import java.io.IOException;
import java.time.LocalDate;

public class ApplifyMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplifyMain.class.getResource("/org/example/applify/applifyViewer.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 900, 800);
        stage.setTitle("Applify Dashboard");
        stage.setScene(scene);
        stage.show();
        System.out.println("test 0-1");
    }

    public static void main(String[] args) {
        launch();
        DatabaseHandler databaseHandler = new DatabaseHandler();
        ApplicationService service = new ApplicationService(databaseHandler);
        System.out.println(databaseHandler.readDatabase());
        databaseHandler.insertIntoDatabase(new JobApplication("Intern", "VARTA", "www.company.com", LocalDate.of(2026,01,26),Status.WaitingForReply));
        databaseHandler.deleteFromDatabase(new JobApplication("Intern", "VARTA", "www.company.com", LocalDate.of(2026,01,26),Status.WaitingForReply));
        databaseHandler.updateDatabase(new JobApplication("Intern", "VARTA", "www.company.com", LocalDate.of(2026,01,26),Status.WaitingForReply), "hired", "BOSCH");
        System.out.println("Test 1"); // magic happens in the launch method
    }
}
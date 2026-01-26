package service;

import database.DatabaseHandler;
import model.JobApplication;

import java.util.List;
import java.util.function.Predicate;

/**
 *
 * This class
 *
 */

public class ApplicationService {
    //Fields
    private List<JobApplication> applicationList;
    private final DatabaseHandler databaseHandler;

    //constructor with dependency injection
    public ApplicationService(DatabaseHandler databaseHandler){
        this.databaseHandler= databaseHandler;
    }

    //methods
    public void readJobApplicationsFromDatabase(){
        //read job applications from database and save them into application list
        applicationList = databaseHandler.readDatabase();
    }
    public void addJobApplication(JobApplication jobApplication){

    }
    public void removeJobApplication(JobApplication jobApplication){

    }
    public void updateJobApplication (JobApplication jobApplication){

    }
    public List<JobApplication> searchJobApplication (int choice){
        return null;
    }
    private Predicate<JobApplication> getPredicateFromChoice(int choice){
        return null;
    }

    // Getters and Setters
    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }
}
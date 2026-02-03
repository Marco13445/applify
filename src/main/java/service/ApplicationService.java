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
        //Copy job applications from database into applicationlist
        readJobApplicationsFromDatabase();

        //Check if the jobApplication to add is already in the list / database
        for(var a : applicationList){
            //If the job application is already in the list/database --> return error
            if(a.getPostingName().equals(jobApplication.getPostingName()) && a.getCompany().equals(jobApplication.getCompany())){
                //For Java-FX
                System.out.println("This job application is already in the database. It can not be added to the database.");
                applicationList.clear();
                return;
            }
        }
        //if the job application is not in the list/database --> call method insertIntoDatabase
       databaseHandler.insertIntoDatabase(jobApplication);

        //Delete application list
        applicationList.clear();
    }
    public void removeJobApplication(JobApplication jobApplication){
        //check if jobApplication is in database
        //Copy job applications from database into applicationlist
        readJobApplicationsFromDatabase();

        //Check if the jobApplication to add is already in the list / database
        for(var a : applicationList){
            // if in database, call delete method
            if(a.getPostingName().equals(jobApplication.getPostingName()) && a.getCompany().equals(jobApplication.getCompany())){
                //For Java-FX
                databaseHandler.deleteFromDatabase(jobApplication);
                applicationList.clear();
                return;
            }
        }
        //For Java-FX
        //if not, handle exception
        System.out.println("The job application with posting name "+ jobApplication.getPostingName() + " at company "+ jobApplication.getCompany() +"" +
                "can not be deleted from the database as it is not existent in the database. ");
        applicationList.clear();
    }


    public void updateJobApplication (JobApplication jobApplication, String newPostingName, String newCompanyName){
        //copy current database entries into list
        readJobApplicationsFromDatabase();

        //Check if jobApplication already in database / list
        for (var a : applicationList){
            if(a.getPostingName().equals(jobApplication.getPostingName()) && a.getCompany().equals(jobApplication.getCompany())){
                //For Java-FX
                databaseHandler.updateDatabase(jobApplication, newPostingName, newCompanyName);
                applicationList.clear();
                return;
            }
        }
        //Java-FX
        //if jobApplication not in database / list
        System.out.println("The job application with posting name "+ jobApplication.getPostingName() + " at company "+ jobApplication.getCompany() +" " +
                "can not be updated in the database as it is not existent in the database. ");
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

    public List<JobApplication> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(List<JobApplication> applicationList) {
        this.applicationList = applicationList;
    }
}
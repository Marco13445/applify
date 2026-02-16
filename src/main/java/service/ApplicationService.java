package service;

import database.DatabaseHandler;
import model.JobApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * This class makes use of the DAO-Layer, which is the DatabaseHandler-class and, thus, offers a service to the user.
 * This class represents the service layer.
 */

public class ApplicationService {
    //Fields
    private List<JobApplication> applicationList;
    private final DatabaseHandler databaseHandler;

    //constructor with dependency injection
    public ApplicationService(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    //methods
    public void readJobApplicationsFromDatabase() {
        //read job applications from database and save them into application list
        applicationList = databaseHandler.readDatabase();
    }

    public void addJobApplication(JobApplication jobApplication) {
        //Copy job applications from database into applicationlist
        readJobApplicationsFromDatabase();

        //Check if the jobApplication to add is already in the list / database
        for (var a : applicationList) {
            //If the job application is already in the list/database --> return error
            if (a.getId() == jobApplication.getId()) {
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

    public void removeJobApplication(JobApplication jobApplication) {
        //check if jobApplication is in database
        //Copy job applications from database into applicationlist
        readJobApplicationsFromDatabase();

        //Check if the jobApplication to add is already in the list / database
        for (var a : applicationList) {
            // if in database, call delete method
            if (a.getId() == jobApplication.getId()) {
                //For Java-FX
                databaseHandler.deleteFromDatabase(jobApplication);
                applicationList.clear();
                return;
            }
        }
        //For Java-FX
        //if not, handle exception
        System.out.println("The job application with posting name " + jobApplication.getPostingName() + " at company " + jobApplication.getCompany() + "" +
                "can not be deleted from the database as it is not existent in the database. ");
        applicationList.clear();
    }


    public void updateJobApplication(JobApplication jobApplication, String newPostingName, String newCompanyName,
                                     String newPostingLink, String newApplicationStatus,
                                     LocalDate newNextInterviewDate, String newNextInterviewLink, String newNextInterviewPlace,
                                     String newContactPersonFullName, String newNotes) {
        //copy current database entries into list
        readJobApplicationsFromDatabase();

        //Check if jobApplication already in database / list
        for (var a : applicationList) {
            if (a.getId() == jobApplication.getId()) {
                //For Java-FX
                databaseHandler.updateDatabase(jobApplication, newPostingName, newCompanyName, newPostingLink, newApplicationStatus,
                        newNextInterviewDate, newNextInterviewLink, newNextInterviewPlace,
                        newContactPersonFullName, newNotes);
                applicationList.clear();
                return;
            }
        }
        //Java-FX
        //if jobApplication not in database / list
        System.out.println("The job application with posting name " + jobApplication.getPostingName() + " at company " + jobApplication.getCompany() + " " +
                "can not be updated in the database as it is not existent in the database. ");
    }

    public ArrayList<JobApplication> searchJobApplication(int choice) {
        ArrayList<JobApplication> searchList = new ArrayList<>();

        switch (choice) {
            case 0:
                searchList= (ArrayList<JobApplication>) applicationList;
                break;

            case 1: //show all applications with a current invitation
                for (JobApplication application : applicationList) {
                    if (application.getApplicationStatus().equals(JobApplication.Status.Invitation))
                        searchList.add(application);
                }
                break;
            case 2: //show all applications from the last 2-3 weeks
                for (JobApplication application : applicationList) {
                    LocalDate date = application.getApplicationDate();
                    LocalDate today = LocalDate.now();
                    int numberOfweeks = 3;
                    if ( date.isAfter(today.minusWeeks(numberOfweeks)) && !date.isAfter(today)){
                        searchList.add(application);
                    }
                }
                break;
        }
        return searchList;
    }

    private Predicate<JobApplication> getPredicateFromChoice(int choice) {
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
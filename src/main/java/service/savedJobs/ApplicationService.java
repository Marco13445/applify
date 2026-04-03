package service.savedJobs;

import database.savedJobs.DatabaseHandlerSavedJobs;
import model.JobApplication;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * This class makes use of the DAO-Layer, which is the DatabaseHandler-class and, thus, offers a service to the user.
 * This class represents the service layer.
 */

public class ApplicationService {
    //Fields
    private List<JobApplication> savedJobsList;
    private final DatabaseHandlerSavedJobs databaseHandlerSavedJobs;

    //constructor with dependency injection
    public ApplicationService(DatabaseHandlerSavedJobs databaseHandlerSavedJobs) {
        this.databaseHandlerSavedJobs = databaseHandlerSavedJobs;
    }

    //methods
    public void readSavedJobsFromDatabase() {
        //read job applications from database and save them into application list
        savedJobsList = databaseHandlerSavedJobs.readDatabase();
    }

    public void addSavedJob(JobApplication jobApplication) {
        //Copy job applications from database into applicationlist
        readSavedJobsFromDatabase();

        //Check if the jobApplication to add is already in the list / database
        for (var a : savedJobsList) {
            //If the job application is already in the list/database --> return error
            if (a.getId() == jobApplication.getId()) {
                //For Java-FX
                System.out.println("This saved job is already in the database. It can not be added to the database.");
                savedJobsList.clear();
                return;
            }
        }
        //if the job application is not in the list/database --> call method insertIntoDatabase
        databaseHandlerSavedJobs.insertIntoDatabase(jobApplication);

        //Delete application list
        savedJobsList.clear();
    }

    public void removeSavedJob(JobApplication jobApplication) {
        //check if jobApplication is in database
        //Copy job applications from database into applicationlist
        readSavedJobsFromDatabase();

        //Check if the jobApplication to add is already in the list / database
        for (var a : savedJobsList) {
            // if in database, call delete method
            if (a.getId() == jobApplication.getId()) {
                //For Java-FX
                databaseHandlerSavedJobs.deleteFromDatabase(jobApplication);
                savedJobsList.clear();
                return;
            }
        }
        //For Java-FX
        //if not, handle exception
        System.out.println("The saved job with posting name " + jobApplication.getPostingName() + " at company " + jobApplication.getCompany() + "" +
                "can not be deleted from the database as it is not existent in the database. ");
        savedJobsList.clear();
    }


    public void updateSavedJob(JobApplication jobApplication, String newPostingName, String newCompanyName,
                               String newPostingLink, String newContactPersonFullName, String newNotes) {
        //copy current database entries into list
        readSavedJobsFromDatabase();

        //Check if jobApplication already in database / list
        for (var a : savedJobsList) {
            if (a.getId() == jobApplication.getId()) {
                //For Java-FX
                databaseHandlerSavedJobs.updateDatabase(jobApplication, newPostingName, newCompanyName, newPostingLink,
                        newContactPersonFullName, newNotes);
                savedJobsList.clear();
                return;
            }
        }
        //Java-FX
        //if jobApplication not in database / list
        System.out.println("The saved job with posting name " + jobApplication.getPostingName() + " at company " + jobApplication.getCompany() + " " +
                "can not be updated in the database as it is not existent in the database. ");
    }

  /**  public ArrayList<JobApplication> createFilterList(int filterCriterion) {
        ArrayList<JobApplication> filterList = new ArrayList<>();

        switch (filterCriterion) {
            case 0: //no filter, i.e. full list/applicationList
                filterList = (ArrayList<JobApplication>) savedJobsList;
                break;

            case 1: //show all applications with a current invitation
                for (JobApplication application : savedJobsList) {
                    if (application.getApplicationStatus().equals(JobApplication.convertStatusToString(JobApplication.Status.Invitation)))
                        filterList.add(application);
                }
                break;
            case 2: //show all applications from the last 2-3 weeks
                for (JobApplication application : savedJobsList) {
                    LocalDate date = application.getApplicationDate();
                    LocalDate today = LocalDate.now();
                    int numberOfweeks = 3;
                    if (date.isAfter(today.minusWeeks(numberOfweeks)) && !date.isAfter(today)) {
                        filterList.add(application);
                    }
                }
                break;
        }
        return filterList;
    }
*/
    public ArrayList<JobApplication> createSearchList(String searchword) throws IllegalAccessException {
        ArrayList<JobApplication> searchList = new ArrayList<>();
        DateTimeFormatter formatterGerman = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter formatterEnglish = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (searchword.equals("")) {
            return null;
        }

        for (JobApplication application : savedJobsList) {
            for (Field field : application.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(application);

                // search in fields that are of type String such as postingName or notes
                if (value instanceof String && ((String) value).toLowerCase().contains(searchword.toLowerCase())) {
                    searchList.add(application);
                    break;
                }
                // search in fields that are of type LocalDate such as application or interview date
                else if (value instanceof LocalDate && (((LocalDate) value).format(formatterGerman).contains(searchword) ||
                        ((LocalDate) value).format(formatterEnglish).contains(searchword))) {
                    searchList.add(application);
                    break;
                }
            }
        }

        return searchList;
    }



    private Predicate<JobApplication> getPredicateFromChoice(int choice) {
        return null;
    }

    // Getters and Setters
    public DatabaseHandlerSavedJobs getDatabaseHandlerSavedJobs() {
        return databaseHandlerSavedJobs;
    }

    public List<JobApplication> getSavedJobsList() {
        return savedJobsList;
    }

    public void setSavedJobsList(List<JobApplication> savedJobsList) {
        this.savedJobsList = savedJobsList;
    }
}
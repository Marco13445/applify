package service;

import database.appliedJobs.DatabaseHandlerAppliedJobs;
import model.JobApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the abstract template for the service classes for applied and saved jobs, respectively.
 * This class makes use of the DAO-Layer, which is the DatabaseHandler-class and, thus, offers a service to the user.
 * This class represents the service layer.
 */


public abstract class ApplicationService {

    /**
     * Create a copy of the database in form of a list
     */
    public abstract void readJobsFromDatabase();

    /**
     * Add a job application to database
     */
    public abstract void addJob(JobApplication jobapplication);

    /**
     * Remove a job application from database
     */
    public abstract void removeJob(JobApplication jobapplication);


    /**
     * Method that creates a list based on chosen filter criterion
     *
     * Only used in ApplicationServiceAppliedJobs.jva
     *
     * @param filterCriterion
     * @return
     */
 //   public abstract ArrayList<JobApplication> createFilterList(int filterCriterion);

    /**
     * Method that creates a list based on typed searchword, used both in ApplicationService for applied and saved jobs
     * @param searchword
     * @return
     * @throws IllegalAccessException
     */
    public abstract ArrayList<JobApplication> createSearchList(String searchword) throws IllegalAccessException;


    /**
     * Method that creates a joint list out of search and filter list
     *
     * Only used in ApplicationServiceAppliedJobs.java
     *
     * @param filterList
     * @param searchList
     * @return
     */
    //public abstract ArrayList <JobApplication> createJointList (ArrayList<JobApplication> filterList, ArrayList<JobApplication> searchList);
}

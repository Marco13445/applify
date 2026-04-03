package database.appliedJobs;

import database.DatabaseHandler;
import javafx.scene.control.DatePicker;
import model.JobApplication;
//import model.Status;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * This class inherits from the abstract class DatabaseHandler
 * implementing specific methods.
 *  This class accesses the table 'applify.appliedJobsList'
 */


public class DatabaseHandlerAppliedJobs extends DatabaseHandler {

    /**
     * This method reads the current entries in the database table 'appliedJobsList' and
     * returns them in a List<JobApplication>
     */
    @Override
    public List<JobApplication> readDatabase() {
        List<JobApplication> appliedJobsList = new ArrayList<JobApplication>();

        //SQL Command reading the whole table
        String sqlSelectCommand = "SELECT * FROM appliedjobslist";

        //Preventing crashes by checking anti-null of credentials
        if (user == null || password == null) {
            throw new RuntimeException("Database environment variables not set. Please configure DB_USER and DB_PASSWORD.");
        };

        //Creating connection to local database
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelectCommand);
            //System.out.println("MySQL-Verbindung erfolgreich");


            //declaring attributes so that they can be used later to get the table contents attributed row per row
            int id = -2;
            String postingName = "";
            String company = "";
            String postingLink = "";
            LocalDate applicationDate = null;
            String applicationStatus = null;

            //new attributes for new columns / new fields
            Object nextInterviewDate = null;
            String nextInterviewLink = "";
            String nextInterviewPlace = "";
            String contactPersonFullName = "";
            String notes = "";

            //reading row per row of database to copy them into just declared attributes
            while (rs.next()) {
                id = rs.getInt("id");
                postingName = rs.getString("postingName");
                company = rs.getString("company");
                postingLink = rs.getString("postingLink");
                applicationDate = rs.getDate("applicationDate").toLocalDate();
                applicationStatus = rs.getString("applicationStatus");

                //new fields/attributes
                if(rs.getDate("nextInterviewDate").equals(Date.valueOf("0001-01-01"))){
                    nextInterviewDate = "";
                }else{
                    nextInterviewDate = rs.getDate("nextInterviewDate").toLocalDate();
                }
                nextInterviewLink = rs.getString("nextInterviewLink");
                nextInterviewPlace = rs.getString("nextInterviewPlace");
                contactPersonFullName = rs.getString("contactPersonFullName");
                notes = rs.getString("notes");

                // Create an instance of JobApplication and add it to the list to be returned by this method
                appliedJobsList.add(new JobApplication(id, postingName, company, postingLink, applicationDate, applicationStatus,
                        nextInterviewDate, nextInterviewLink, nextInterviewPlace, contactPersonFullName, notes));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection to Database has not been successful. ");
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("An exception other than an SQLException has occured. ");
        }
        return appliedJobsList;
    }

    /**
     * Insert a jobApplication into the respective database table
     * @param jobApplication
     */
    @Override
    public void insertIntoDatabase(JobApplication jobApplication) {

        //sql connection
        String sqlInsertCommand = "INSERT INTO appliedjobslist (postingName, company, postingLink, applicationDate, applicationStatus, " +
                "nextInterviewDate, nextInterviewLink, nextInterviewPlace, contactPersonFullName, notes)\n" +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";

        //Preventing crashes by checking anti-null of credentials
        if (user == null || password == null) {
            throw new RuntimeException("Database environment variables not set. Please configure DB_USER and DB_PASSWORD.");
        };

        //create connection to Database
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement statement = connection.prepareStatement(sqlInsertCommand);

            //substitute ?-tags with attributes of the parameter jobApplication
            //statement.setInt(1, jobApplication.getId());
            statement.setString(1, jobApplication.getPostingName());
            statement.setString(2, jobApplication.getCompany());
            statement.setString(3, jobApplication.getPostingLink().toString());
            if(jobApplication.getApplicationDate() == null){
                statement.setDate(4, Date.valueOf(LocalDate.now()));
            }else{
                statement.setDate(4, new java.sql.Date(Date.from(jobApplication.getApplicationDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            }
            statement.setString(5, jobApplication.getApplicationStatus());
            //new attributes
            if(jobApplication.getNextInterviewDate() == ""){
                statement.setDate(6, Date.valueOf (LocalDate.of(0,1,1)));
            }else{
                statement.setDate(6, new java.sql.Date(Date.from( ((LocalDate) jobApplication.getNextInterviewDate()).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            }
            statement.setString(7, jobApplication.getNextInterviewLink());
            statement.setString(8, jobApplication.getNextInterviewPlace());
            statement.setString(9, jobApplication.getContactPersonFullName());
            statement.setString(10, jobApplication.getNotes());

            //Execute statement, which is an 'Insert'-Statement
            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection to Database has not been successful. ");
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("An exception other than an SQLException has occured. ");
        }

        System.out.println("Applied Job successfully added to Database. ");
    }

    /**
     * Delete respective job application from respective database table
     * @param jobApplication
     */
    @Override
    public void deleteFromDatabase(JobApplication jobApplication) {
        //sql command for deletion
        String sqlDeleteCommand = "DELETE FROM appliedJobsList WHERE id = ?";

        //Preventing crashes by checking anti-null of credentials
        if (user == null || password == null) {
            throw new RuntimeException("Database environment variables not set. Please configure DB_USER and DB_PASSWORD.");
        };

        //create connection
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement statement = connection.prepareStatement(sqlDeleteCommand);

            //substitute ?-tags with attributes of the parameter jobApplication
            statement.setInt(1, jobApplication.getId());

            //Execute statement, which is a 'Delete'-statement
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection to Database has not been successful. ");
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("An exception other than an SQL-Exception has occured. ");
        }
        System.out.println("Job application with posting name " + jobApplication.getPostingName() + " from company " +
                jobApplication.getCompany() + " has been removed from database. ");
    }

    /**
    //We have to make sure that when no entry is made while editing an application
    //the former value remains. Otherwise, the new value (which was entered by the user) is used.
    //THese declarations are for the two methdos 'updateDatabase()' and 'antiNullUpdate()'
    */

    String postingName, companyName, postingLink, applicationStatus;
    Date nextInterviewDate;
    String nextInterviewLink, nextInterviewPlace, contactPersonFullName, notes;


    public void updateDatabase(JobApplication jobApplication, String newPostingName, String newCompanyName,
                               String newPostingLink, String newApplicationStatus,
                               Object newNextInterviewDate,  DatePicker datePicker, String newNextInterviewLink, String newNextInterviewPlace,
                               String newContactPersonFullName, String newNotes)
    {

        //sql command for update
        String sqlUpdateCommand = "UPDATE  appliedJobsList " +
                "SET postingName = ?, " + //?-tag has to use the NEW postingName
                " company = ?," + //?-tag has to use the NEW company
                " postingLink = ?, " +
                " applicationDate = ?," +
                " applicationStatus = ?, " +
                " nextInterviewDate = ?, " +
                " nextInterviewLink = ?, " +
                " nextInterviewPlace = ?, " +
                " contactPersonFullName = ?, " +
                " notes = ? " +
                " WHERE id = ? "; //here, the ?-tag has to use the selected ID

        //Preventing crashes by checking anti-null of credentials
        if (user == null || password == null) {
            throw new RuntimeException("Database environment variables not set. Please configure DB_USER and DB_PASSWORD.");
        };

        antiNullUpdate(jobApplication,  newPostingName,  newCompanyName,
                     newPostingLink,  newApplicationStatus,
                newNextInterviewDate, datePicker,  newNextInterviewLink,  newNextInterviewPlace,
                newContactPersonFullName, newNotes   );

        //create connection
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement statement = connection.prepareStatement(sqlUpdateCommand);

            //substitute ?-tags with attributes of the parameter jobApplication
            statement.setString(1, postingName);
            statement.setString(2, companyName);
            statement.setString(3, postingLink);
            // to understand
            statement.setDate(4, new java.sql.Date(Date.from(jobApplication.getApplicationDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            statement.setString(5, applicationStatus);

            //new attributes
            //to understand
            statement.setDate(6, nextInterviewDate);
            statement.setString(7, nextInterviewLink);
            statement.setString(8, nextInterviewPlace);
            statement.setString(9, contactPersonFullName);
            statement.setString(10, notes);
            //getId()
            statement.setInt(11, jobApplication.getId());

            //Execute statement, which is a 'Delete'-statement
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection to Database has not been successful. ");
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("An exception other than an SQL-Exception has occured. ");
        }
        System.out.println("Job application with posting name " + jobApplication.getPostingName() + " from company " +
                jobApplication.getCompany() + " has been updated in database with new posting name "+ newPostingName +
                " and new company name "+ newCompanyName + ". ");
    }

    //outsourced method used in 'updateDatabase()'

    protected void antiNullUpdate(JobApplication jobApplication, String newPostingName, String newCompanyName,
                                  String newPostingLink, String newApplicationStatus,
                                  Object newNextInterviewDate,  DatePicker datePicker, String newNextInterviewLink, String newNextInterviewPlace,
                                  String newContactPersonFullName, String newNotes) {


        if(newPostingName.equals("")){
            postingName = jobApplication.getPostingName();
        }else{
            postingName = newPostingName;
        }
        if(newCompanyName.equals("")){
            companyName = jobApplication.getCompany();
        }else{
            companyName = newCompanyName;
        }
        if(newPostingLink.equals("")){
            postingLink = jobApplication.getPostingLink().toString();
        }else{
            postingLink = newPostingLink;
        }
        if(newApplicationStatus.equals("")){
            applicationStatus = jobApplication.getApplicationStatus();
        }else{
            applicationStatus = newApplicationStatus;
        }
        if(newNextInterviewDate == ""){
            nextInterviewDate = Date.valueOf (LocalDate.of(0,1,1));
        }else{
            nextInterviewDate = new java.sql.Date(Date.from( (updateDate(jobApplication.getNextInterviewDate(),datePicker)).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
        }
        if(newNextInterviewLink.equals("")){
            nextInterviewLink = jobApplication.getNextInterviewLink();
        }else{
            nextInterviewLink = newNextInterviewLink;
        }
        if(newNextInterviewPlace.equals("")){
            nextInterviewPlace = jobApplication.getNextInterviewPlace();
        }else{
            nextInterviewPlace = newNextInterviewPlace;
        }
        if(newContactPersonFullName.equals("")){
            contactPersonFullName= jobApplication.getContactPersonFullName();
        }else{
            contactPersonFullName = newContactPersonFullName;
        }
        if(newNotes.equals("")){
            notes = jobApplication.getNotes();
        }else{
            notes = newNotes;
        }

    }

    /**
     * This method makes sure that if in the Date Picker the user chooses no date,
     * a default date in the year 0 is used or the respective current date value.
     *
     * @param currentValue
     * @param newValue
     * @return
     */
    @Override
    protected LocalDate updateDate(Object currentValue, DatePicker newValue) {
        if ((currentValue == null || currentValue.toString().isEmpty()) && "".equals(newValue)) {
            return LocalDate.of(0, 1, 1);
        }else if( (currentValue == null || currentValue.toString().isEmpty()) && (newValue!=null)){
            return newValue.getValue();
        }else if ( (currentValue != null ) && "".equals(newValue)){
            return (LocalDate) currentValue;
        }else { //( (currentValue != null ) && (newValue!=null) ){
            return newValue.getValue();
        }
    }
}





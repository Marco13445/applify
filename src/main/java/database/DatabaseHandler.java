package database;

import model.JobApplication;
//import model.Status;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static model.JobApplication.convertStatusToString;
import static model.JobApplication.convertStringToStatus;

/**
 * This is only class directly accessing the database (through methods with sql-queries).
 * So it results into a clear localisation of methods with this specific purpose.
 *  It is called 'Data-Access-Object'-Layer (DAO).
 */


public class DatabaseHandler {

    //fields
    //database specific connection data
    String url = "jdbc:mysql://localhost:3306/applify";
    String user = "root";
    String password = "4444";


    /**
     * This method reads the current entries in the database and returns them in a List<JobApplication>
     */
    public List<JobApplication> readDatabase() {
        List<JobApplication> appliedJobsList = new ArrayList<JobApplication>();

        //SQL Command reading the whole table
        String sqlSelectCommand = "SELECT * FROM appliedjobslist";

        // Wenn Datenbank nicht existiert, gibt leere Liste zurück // später implementieren
        /*if (!File.Exists(filePath))
            return animals;
        */
        //Wenn tabelle leer, dann leere Liste zurück
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
            LocalDate nextInterviewDate = null;
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
                nextInterviewDate = rs.getDate("nextInterviewDate").toLocalDate();
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

    public void insertIntoDatabase(JobApplication jobApplication) {

        //sql connection
        String sqlInsertCommand = "INSERT INTO appliedjobslist (postingName, company, postingLink, applicationDate, applicationStatus, " +
                "nextInterviewDate, nextInterviewLink, nextInterviewPlace, contactPersonFullName, notes)\n" +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";




        //create connection to Database
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement statement = connection.prepareStatement(sqlInsertCommand);

            //substitute ?-tags with attributes of the parameter jobApplication
            //statement.setInt(1, jobApplication.getId());
            statement.setString(1, jobApplication.getPostingName());
            statement.setString(2, jobApplication.getCompany());
            statement.setString(3, jobApplication.getPostingLink());
            if(jobApplication.getApplicationDate() == null){
                statement.setDate(4, Date.valueOf(LocalDate.now()));
            }else{
                statement.setDate(4, new java.sql.Date(Date.from(jobApplication.getApplicationDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            }
            statement.setString(5, jobApplication.getApplicationStatus());
            //new attributes
            if(jobApplication.getNextInterviewDate() == null){
                statement.setDate(6, Date.valueOf (LocalDate.of(1990,1,1)));
            }else{
                statement.setDate(6, new java.sql.Date(Date.from(jobApplication.getNextInterviewDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
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

    public void deleteFromDatabase(JobApplication jobApplication) {
        //sql command for deletion
        String sqlDeleteCommand = "DELETE FROM appliedJobsList WHERE id = ?";

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

    //We have to make sure that when no entry is made while editing an application
    //the former value remains. Otherwise, the new value (which was entered by the user) is used.
    //THese declarations are for the two methdos 'updateDatabase()' and 'antiNullUpdate()'
    String postingName, companyName, postingLink, applicationStatus;
    LocalDate nextInterviewDate;
    String nextInterviewLink, nextInterviewPlace, contactPersonFullName, notes;


    public void updateDatabase(JobApplication jobApplication, String newPostingName, String newCompanyName,
                               String newPostingLink, String newApplicationStatus,
                               LocalDate newNextInterviewDate, String newNextInterviewLink, String newNextInterviewPlace,
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

        antiNullUpdate(jobApplication,  newPostingName,  newCompanyName,
                     newPostingLink,  newApplicationStatus,
                     newNextInterviewDate,  newNextInterviewLink,  newNextInterviewPlace,
                     newContactPersonFullName,  newNotes);

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
            statement.setDate(6, new java.sql.Date(Date.from(jobApplication.getNextInterviewDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));

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
    private void antiNullUpdate(JobApplication jobApplication,String newPostingName, String newCompanyName,
                                String newPostingLink, String newApplicationStatus,
                                LocalDate newNextInterviewDate, String newNextInterviewLink, String newNextInterviewPlace,
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
            postingLink = jobApplication.getPostingLink();
        }else{
            postingLink = newPostingLink;
        }
        if(newApplicationStatus.equals("")){
            applicationStatus = jobApplication.getApplicationStatus();
        }else{
            applicationStatus = newApplicationStatus;
        }
        if(newNextInterviewDate == null){
            nextInterviewDate = jobApplication.getNextInterviewDate();
        }else{
            nextInterviewDate = newNextInterviewDate;
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
}

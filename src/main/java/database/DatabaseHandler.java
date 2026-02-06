package database;

import model.JobApplication;
import model.Status;

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
    String url = "jdbc:mysql://localhost:3306/joblify-dashboard";
    String user = "root";
    String password = "4444";

    //default constructor
    public DatabaseHandler() {
    }

    ;

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
            Status applicationStatus = null;

            //reading row per row of database to copy them into just declared attributes
            while (rs.next()) {
                id = rs.getInt("id");
                postingName = rs.getString("postingName");
                company = rs.getString("company");
                postingLink = rs.getString("postingLink");
                applicationDate = rs.getDate("applicationDate").toLocalDate();
                applicationStatus = convertStringToStatus(rs.getString("applicationStatus"));

                // Create an instance of JobApplication and add it to the list to be returned by this method
                appliedJobsList.add(new JobApplication(id, postingName, company, postingLink, applicationDate, applicationStatus));
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
        String sqlInsertCommand = "INSERT INTO appliedjobslist( postingName, company, postingLink, applicationDate, applicationStatus)" +
                "VALUES (?,?,?,?,?)";

        //create connection to Database
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement statement = connection.prepareStatement(sqlInsertCommand);

            //substitute ?-tags with attributes of the parameter jobApplication
            //statement.setInt(1, jobApplication.getId());
            statement.setString(1, jobApplication.getPostingName());
            statement.setString(2, jobApplication.getCompany());
            statement.setString(3, jobApplication.getPostingLink());
            statement.setDate(4, new java.sql.Date(Date.from(jobApplication.getApplicationDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            statement.setString(5, convertStatusToString(jobApplication.getApplicationStatus()));

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

    public void updateDatabase(JobApplication jobApplication, String newPostingName, String newCompanyName,
                               String newPostingLink, String newApplicationStatus) {

        //sql command for update
        String sqlUpdateCommand = "UPDATE  appliedJobsList " +
                "SET postingName = ?, " + //?-tag has to use the NEW postingName
                " company = ?," + //?-tag has to use the NEW company
                " postingLink = ?, " +
                " applicationDate = ?," +
                " applicationStatus = ? " +
                " WHERE id = ? "; //here, the ?-tag has to use the selected ID

        //We have to make sure that when no entry is made while editing an application
        //the former value remains. Otherwise, the new value (which was entered by the user) is used.
        String postingName, companyName, postingLink, applicationStatus;

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
            applicationStatus = convertStatusToString(jobApplication.getApplicationStatus());
        }else{
            applicationStatus = newApplicationStatus;
        }

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
            statement.setInt(6, jobApplication.getId());

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
}

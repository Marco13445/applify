package database;

import javafx.scene.control.DatePicker;
import model.JobApplication;

import javax.print.attribute.standard.JobKOctets;
import java.time.LocalDate;
import java.util.List;

/**
 * This abstract class is to serve as a template for the database handler classes
 * for the database tables 'appliedJobsList' and for the database 'savedJobsList', respectively.
 */

public abstract class DatabaseHandler {

    /**
     * database specific connection data - same for both tables
     */
    protected String url = "jdbc:mysql://localhost:3306/applify";
    protected static final  String user =
            System.getenv().getOrDefault("DB_USER", "root");
    protected static final String password = System.getenv("DB_PASSWORD");

    /**
     * This method reads the current entries in one of the database tables specified in the code
     * and returns them in a List<JobApplication>
     */
    public abstract List<JobApplication> readDatabase();

    /**
     * Insert a jobApplication into the respective database table
     * @param jobApplication
     */
    public abstract void insertIntoDatabase(JobApplication jobApplication);

    /**
     * Delete respective job application from respective database table
     * @param jobApplication
     */
    public abstract void deleteFromDatabase(JobApplication jobApplication);

    /**
     * 'update database(...)' is due to different parameters child-specific,
     * and thus, not defined here in the abstract parent class
     */


    /**
     *
     *     We have to make sure that when no entry is made while editing an application
     *     the former value remains. Otherwise, the new value (which was entered by the user) is used.
     *     These declarations are for the two methdos 'updateDatabase()' and 'antiNullUpdate()'
     *     These methods are child specific and not defined here
        */


    /**
     * This method makes sure that if in the Date Picker the user chooses no date,
     * a default date in the year 0 is used or the respective current date value.
     *
     * @param currentValue
     * @param newValue
     * @return
     */
    protected abstract LocalDate updateDate (Object currentValue, DatePicker newValue);

}
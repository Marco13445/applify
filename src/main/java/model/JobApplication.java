package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDate;

/**
 * This class definition defines a job application. It consists of jopposting relevant information
 * like name of the jop position as well as information about the application for such a job post
 * e.g. application status.
 *
 */
public class JobApplication {

    //fields
    private int id =-1;
    private String postingName;
    private String company;
    private String postingLink;
    private LocalDate applicationDate;
    private Status applicationStatus = Status.WaitingForReply;

    //

    //constructor
    //reflect, how the details should be entered by the user
    //probably through a window in JavaFX
    public JobApplication(int id, String postingName, String company, String postingLink, LocalDate applicationDate, Status applicationStatus){
        this.id = id;
        this.postingName=postingName;
        this.company=company;
        this.postingLink=postingLink;
        this.applicationDate= applicationDate;
        this.applicationStatus = applicationStatus;
    }

    //Methods
    public static String convertStatusToString(Status applicationStatus){
        if(applicationStatus.equals(Status.WaitingForReply)){
            return "Waiting for reply";
        }else if (applicationStatus.equals(Status.Invitation)) {
            return "Invitation";
        }else if (applicationStatus.equals(Status.Offer)){
            return "Offer";
        }else if(applicationStatus.equals(Status.Rejected)) {
            return "Rejected";
        }else {
            return "Withdrawn";
        }
    };
    public static Status convertStringToStatus(String applicationStatusInDatabase){
        if(applicationStatusInDatabase.equals("Waiting for reply")){
            return Status.WaitingForReply;
        }else if (applicationStatusInDatabase.equals("Invitation")){
            return Status.Invitation;
        }else if (applicationStatusInDatabase.equals("Offer")){
            return Status.Offer;
        }else if(applicationStatusInDatabase.equals("Rejected")){
            return Status.Rejected;
        }else if (applicationStatusInDatabase.equals("Withdrawn")){
            return Status.Withdrawn;
        }else{
            System.out.println("The given String-Parameter does not correspond to a pre-defined application Status. ");
        }
        return null;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostingName() {
        return postingName;
    }

    public void setPostingName(String postingName) {
        this.postingName = postingName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPostingLink() {
        return postingLink;
    }

    public void setPostingLinks(String postingLinks) {
        this.postingLink = postingLinks;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Status getApplicationStatus() {
        return applicationStatus;
    }

    //make a drop down menu
    public void setApplicationStatus(Status applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}

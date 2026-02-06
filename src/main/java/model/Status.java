package model;

/**
 *
 * This enum class defines five states (Status) of an application. It is relevant for the property 'applicationStatus'
 * of the class 'jobApplication'.
 *
 */


public enum Status {
    WaitingForReply,
    Invitation,
    Rejected,
    Offer,
    Withdrawn
}

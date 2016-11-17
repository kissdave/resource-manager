package hu.bme.simonyi.dave.resourcemanager.model;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by dkiss on 2016. 09. 27..
 */
@Entity
@Audited
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer requestID;

    @NotNull
    private String eventName;

    private String eventDescription;

    @NotNull
    private Date dateFrom;

    @NotNull
    private Date dateTo;

    private Date handleBefore;

    private Date handleAfter;

    private String comment;

    /* --- Connections --- */

    @ManyToOne
    private Resource resource;

    @ManyToOne
    private RequestStatus requestStatus;

    @ManyToOne
    private User user;


    /* --- Constructors --- */
    public Request() {
        this("", "", null, null, null, null, "", null, null, null);
    }

    public Request(String eventName, String eventDescription, Date dateFrom, Date dateTo, Date handleBefore, Date handleAfter, String comment, Resource resource, RequestStatus requestStatus, User user) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.handleBefore = handleBefore;
        this.handleAfter = handleAfter;
        this.comment = comment;
        this.resource = resource;
        this.requestStatus = requestStatus;
        this.user = user;
    }


    /* --- Getters & Setters */

    public Integer getRequestID() {
        return requestID;
    }

    public void setRequestID(Integer requestID) {
        this.requestID = requestID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getHandleBefore() {
        return handleBefore;
    }

    public void setHandleBefore(Date handleBefore) {
        this.handleBefore = handleBefore;
    }

    public Date getHandleAfter() {
        return handleAfter;
    }

    public void setHandleAfter(Date handleAfter) {
        this.handleAfter = handleAfter;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateFields(Request request) {
        this.eventName = request.getEventName();
        this.eventDescription = request.getEventDescription();
        this.dateFrom = request.getDateFrom();
        this.dateTo = request.getDateTo();
        this.handleBefore = request.getHandleBefore();
        this.handleAfter = request.getHandleAfter();
        this.comment = request.getComment();
        }
}

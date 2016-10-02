package hu.bme.simonyi.dave.resourcemanager.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by dkiss on 2016. 09. 27..
 */
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer requestID;

    @NotNull
    private String eventName;

    private String eventDescription;

    private Date dateFrom;

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
}

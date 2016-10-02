package hu.bme.simonyi.dave.resourcemanager.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by dkiss on 2016. 09. 27..
 */
@Entity
public class RequestStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer requestStatusID;

    @NotNull
    private String name;

    private String description;

    /* --- Connections --- */

    @OneToMany(mappedBy = "requestStatus", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Request> requests;

    /* --- Getters & Setters --- */

    public Integer getRequestStatusID() {
        return requestStatusID;
    }

    public void setRequestStatusID(Integer requestStatusID) {
        this.requestStatusID = requestStatusID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}

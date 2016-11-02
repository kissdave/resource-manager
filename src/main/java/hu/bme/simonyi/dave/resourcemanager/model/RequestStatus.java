package hu.bme.simonyi.dave.resourcemanager.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    public static final String NEW = "Új (nem elfogadott)";
    public static final String APPROVED = "Elfogadott";
    public static final String DECLINED = "Elutasított";
    public static final String UPDATED = "Módosított";
    public static final String CANCELLED = "Visszavont";
    public static final String ARCHIVED = "Archivált";
    public static final String DELETED = "Törölt";
    
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

    public void addRequest(Request request) {
        if(requests == null) {
            requests = new ArrayList<>();
        }

        requests.add(request);
        request.setRequestStatus(this);
    }

    public void removeRequest(Request request) {
        if(requests == null) {
            return;
        }

        requests.remove(request);
        request.setRequestStatus(null);
    }
}

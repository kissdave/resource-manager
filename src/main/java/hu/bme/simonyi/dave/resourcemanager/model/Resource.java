package hu.bme.simonyi.dave.resourcemanager.model;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkiss on 2016. 09. 26..
 */
@Entity
@Audited
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer resourceID;

    @NotNull
    private String resourceName;

    private String description;

    private Boolean active;

    private Boolean archived;

    /* --- Connections --- */

    @ManyToOne
    private ResourceType resourceType;

    @OneToMany(mappedBy = "resource", cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<ResourceFault> resourceFaults;

    @OneToMany(mappedBy = "resource", cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Request> requests;


    /* --- Constructors ---*/

    public Resource() {
        // Just for DB reasons.
        resourceName = "";
        description = "";
        active = false;
        archived = false;
    }

    /* --- Getters & Setters */

    public Integer getResourceID() {
        return resourceID;
    }

    public void setResourceID(Integer resourceID) {
        this.resourceID = resourceID;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public List<ResourceFault> getResourceFaults() {
        return resourceFaults;
    }

    public void setResourceFaults(List<ResourceFault> resourceFaults) {
        this.resourceFaults = resourceFaults;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public void addResourceFault(ResourceFault resourceFault) {
        if(resourceFaults == null) {
            resourceFaults = new ArrayList<>();
        }

        resourceFaults.add(resourceFault);
        resourceFault.setResource(this);
    }

    public void addRequest(Request request) {
        if(requests == null) {
            requests = new ArrayList<>();
        }

        requests.add(request);
        request.setResource(this);
    }
    public void removeRequest(Request request) {
        if(requests == null) {
            return;
        }

        requests.remove(request);
        request.setResource(null);
    }
}

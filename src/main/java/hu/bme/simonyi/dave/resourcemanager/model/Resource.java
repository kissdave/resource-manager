package hu.bme.simonyi.dave.resourcemanager.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by dkiss on 2016. 09. 26..
 */
@Entity
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer resourceID;

    @NotNull
    private String resourceName;

    private String description;

    @NotNull
    private Boolean isActive;

    @NotNull
    private Boolean isArchived;

    /* --- Connections --- */

    @ManyToOne
    private ResourceType resourceType;

    @OneToMany(mappedBy = "resource", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<ResourceFault> resourceFaults;

    @OneToMany(mappedBy = "resource", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Request> requests;


    /* --- Constructors ---*/

    public Resource() {
        // Just for DB reasons.
        resourceName = "";
        description = "";
        isActive = false;
        isArchived = false;
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
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
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

}

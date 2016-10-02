package hu.bme.simonyi.dave.resourcemanager.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by dkiss on 2016. 09. 26..
 */
@Entity
public class ResourceFault {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer resourceFaultID;

    @NotNull
    private String shortDescription;

    private String longDescription;

    private Boolean isActive;

    /* --- Connections --- */

    @ManyToOne
    private Resource resource;

    /* --- Getters & setters --- */
    public Integer getResourceFaultID() {
        return resourceFaultID;
    }

    public void setResourceFaultID(Integer resourceFaultID) {
        this.resourceFaultID = resourceFaultID;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}

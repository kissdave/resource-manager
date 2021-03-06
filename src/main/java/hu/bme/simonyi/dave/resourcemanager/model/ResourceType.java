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
public class ResourceType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer resourceTypeID;

    @NotNull
    private String name;

    private String description;

    /* --- Connections --- */

    @OneToMany(mappedBy = "resourceType", cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Resource> resources;

    /* --- Getters & Setters */

    public Integer getResourceTypeID() {
        return resourceTypeID;
    }

    public void setResourceTypeID(Integer resourceTypeID) {
        this.resourceTypeID = resourceTypeID;
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

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void addResource(Resource resource) {
        if(resources == null) {
            resources = new ArrayList<>();
        }

        resources.add(resource);
        resource.setResourceType(this);
    }

    public void deleteResource(Resource resource) {
        if(resources == null) {
            resources = new ArrayList<>();
            return;
        }

        resources.remove(resource);
        resource.setResourceType(null);
    }
}

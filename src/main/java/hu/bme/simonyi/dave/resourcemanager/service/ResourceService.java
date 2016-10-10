package hu.bme.simonyi.dave.resourcemanager.service;

import hu.bme.simonyi.dave.resourcemanager.model.Resource;
import hu.bme.simonyi.dave.resourcemanager.model.ResourceType;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceRepository;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by dkiss on 2016. 10. 07..
 */
@Service
public class ResourceService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ResourceTypeRepository resourceTypeRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @Transactional
    public void createResource(Resource resource) {
        if(resource.getArchived()) {
            resource.setActive(false);
        }

        em.persist(resource);
    }

    @Transactional
    public void createResource(Resource resource, Integer resourceTypeID) {
        final ResourceType resourceType = resourceTypeRepository.findOne(resourceTypeID);
        resourceType.addResource(resource);
        createResource(resource);
    }

    @Transactional
    public void changeActive(Integer resourceID) {
        final Resource resource = resourceRepository.findOne(resourceID);
        resource.setActive(!resource.getActive());
        em.persist(resource);
    }

    @Transactional
    public void changeArchive(Integer id) {
        final Resource resource = resourceRepository.findOne(id);
        if (!resource.getArchived()) {
            resource.setArchived(true);
            resource.setActive(false);
        } else {
            resource.setArchived(false);
        }
        em.persist(resource);
    }

    @Transactional
    public void updateResource(Resource resource) {

        if(resource.getArchived()) {
            resource.setActive(false);
        }

        em.merge(resource);
    }

    @Transactional
    public void updateResource(Resource resource, Integer resourceTypeID) {
        final Resource resourceOld = resourceRepository.findOne(resource.getResourceID());
        ResourceType resourceTypeOld = resourceOld.getResourceType();
        final ResourceType resourceTypeNew = resourceTypeRepository.findOne(resourceTypeID);

        resourceTypeOld.deleteResource(resourceOld);
        resourceTypeNew.addResource(resource);

        updateResource(resource);
    }
}

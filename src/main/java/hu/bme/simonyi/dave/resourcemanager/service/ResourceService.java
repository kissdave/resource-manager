package hu.bme.simonyi.dave.resourcemanager.service;

import hu.bme.simonyi.dave.resourcemanager.model.Resource;
import hu.bme.simonyi.dave.resourcemanager.model.ResourceType;
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

    @Transactional
    public void createResource(Resource resource) {
        em.persist(resource);
    }

    @Transactional
    public void createResource(Resource resource, Integer resourceTypeID) {
        final ResourceType resourceType = resourceTypeRepository.findOne(resourceTypeID);
        resourceType.addResource(resource);
        createResource(resource);
    }
}

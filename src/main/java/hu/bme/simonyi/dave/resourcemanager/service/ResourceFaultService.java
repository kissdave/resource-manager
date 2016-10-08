package hu.bme.simonyi.dave.resourcemanager.service;

import hu.bme.simonyi.dave.resourcemanager.model.Resource;
import hu.bme.simonyi.dave.resourcemanager.model.ResourceFault;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by dkiss on 2016. 10. 08..
 */
@Service
public class ResourceFaultService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ResourceRepository resourceRepository;

    @Transactional
    public void createFault(ResourceFault resourceFault) {
        em.persist(resourceFault);
    }

    @Transactional
    public void createFault(ResourceFault resourceFault, Integer resourceID) {
        final Resource resource = resourceRepository.findOne(resourceID);
        resource.addResourceFault(resourceFault);
        createFault(resourceFault);
    }
}

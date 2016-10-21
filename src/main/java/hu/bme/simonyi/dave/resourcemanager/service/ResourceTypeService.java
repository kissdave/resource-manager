package hu.bme.simonyi.dave.resourcemanager.service;

import hu.bme.simonyi.dave.resourcemanager.model.ResourceType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by dkiss on 2016. 10. 10..
 */
@Service
public class ResourceTypeService {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void createResourceType(ResourceType resourceType) {
        em.persist(resourceType);
    }

    @Transactional
    public void updateResourceType() {
        // TODO
    }

}

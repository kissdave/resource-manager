package hu.bme.simonyi.dave.resourcemanager.repository;

import hu.bme.simonyi.dave.resourcemanager.model.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dkiss on 2016. 09. 27..
 */
public interface ResourceTypeRepository extends JpaRepository<ResourceType, Integer> {
}

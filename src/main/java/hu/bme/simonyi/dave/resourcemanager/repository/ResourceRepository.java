package hu.bme.simonyi.dave.resourcemanager.repository;

import hu.bme.simonyi.dave.resourcemanager.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dkiss on 2016. 09. 26..
 */
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
}

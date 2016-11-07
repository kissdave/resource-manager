package hu.bme.simonyi.dave.resourcemanager.repository;

import hu.bme.simonyi.dave.resourcemanager.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dkiss on 2016. 11. 07..
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}

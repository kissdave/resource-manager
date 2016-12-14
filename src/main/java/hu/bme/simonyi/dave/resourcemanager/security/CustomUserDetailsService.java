package hu.bme.simonyi.dave.resourcemanager.security;

import hu.bme.simonyi.dave.resourcemanager.model.User;
import hu.bme.simonyi.dave.resourcemanager.model.UserRole;
import hu.bme.simonyi.dave.resourcemanager.repository.UserRepository;
import hu.bme.simonyi.dave.resourcemanager.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dkiss on 2016. 11. 07..
 */
@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    public CustomUserDetailsService() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByusername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user exists with username: " + username);
        } else {
            return new CustomUserDetails(user);
        }

    }
}

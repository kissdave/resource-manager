package hu.bme.simonyi.dave.resourcemanager.test.repository;

import hu.bme.simonyi.dave.resourcemanager.config.JPAConfig;
import hu.bme.simonyi.dave.resourcemanager.model.User;
import hu.bme.simonyi.dave.resourcemanager.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dkiss on 2016. 12. 01..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
public class UserRepositoryIT {

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByusernameIT() {
        for (int i = 1; i <= 5; i++) {
            User user = new User();;
            user.setUsername("Test User" + i);
            user.setPassword("passWD");
            user.setEmail("test" + i + "@example.com");
            user.setMobile("+36123456789");
            user.setEnabled(0);
            if (i % 2 == 0) {
                user.setEnabled(1);
            }
            userRepository.save(user);
        }

        List<User> users = userRepository.findAll();

        User UserFromDB = userRepository.findByusername("Test User2");

        Assert.assertEquals("Test User2", UserFromDB.getUsername());
        Assert.assertTrue(UserFromDB.getEmail().equals("test2@example.com"));
        Assert.assertTrue(UserFromDB.getEnabled() == 1);

    }
}

package by.tms.diplomrestc51.service;

import by.tms.diplomrestc51.entity.Role;
import by.tms.diplomrestc51.entity.User;
import by.tms.diplomrestc51.enums.Status;
import by.tms.diplomrestc51.exception.InvalidException;
import by.tms.diplomrestc51.repository.RoleRepository;
import by.tms.diplomrestc51.repository.UserRepository;
import by.tms.diplomrestc51.validation.IdValidation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    void isDeletedTrue() {
        User user = new User();

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("USER");
        roles.add(role);

        user.setFirstName("userFirstName");
        user.setLastName("userLastName");
        user.setUsername("userUsername");
        user.setPassword("userPassword");
        user.setRoles(roles);
        role.setUser(user);
        user.setPassword("userPassword");
        user.setEmail("user@gmail.com");
        user.setStatus(Status.ACTIVE);
        user.setPhone("+375291234567");

        User saveActive = userRepository.save(user);
        Role saveRole = roleRepository.save(role);

        saveActive.setStatus(Status.DELETED);
        User saveDeleted = userRepository.save(saveActive);

        assertTrue(userService.isDeleted(saveDeleted));

    }

    @Test
    void isDeletedFalse() {
        User user = new User();

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("USER");
        roles.add(role);

        user.setFirstName("userFirstName");
        user.setLastName("userLastName");
        user.setUsername("userUsername");
        user.setPassword("userPassword");
        user.setRoles(roles);
        role.setUser(user);
        user.setPassword("userPassword");
        user.setEmail("user@gmail.com");
        user.setStatus(Status.ACTIVE);
        user.setPhone("+375291234567");

        User saveActive = userRepository.save(user);
        Role saveRole = roleRepository.save(role);

        Exception exception = assertThrows(InvalidException.class, () -> {
            userService.isDeleted(saveActive);
        });

        String expectedMessage = "User is not deleted";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void isActiveTrue() {
        User user = new User();

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("USER");
        roles.add(role);

        user.setFirstName("userFirstName");
        user.setLastName("userLastName");
        user.setUsername("userUsername");
        user.setPassword("userPassword");
        user.setRoles(roles);
        role.setUser(user);
        user.setPassword("userPassword");
        user.setEmail("user@gmail.com");
        user.setStatus(Status.ACTIVE);
        user.setPhone("+375291234567");

        User saveActive = userRepository.save(user);
        Role saveRole = roleRepository.save(role);

        assertTrue(userService.isActive(saveActive));
    }

    @Test
    void isActiveFalseNotActive() {
        User user = new User();

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("USER");
        roles.add(role);

        user.setFirstName("userFirstName");
        user.setLastName("userLastName");
        user.setUsername("userUsername");
        user.setPassword("userPassword");
        user.setRoles(roles);
        role.setUser(user);
        user.setPassword("userPassword");
        user.setEmail("user@gmail.com");
        user.setStatus(Status.NOT_ACTIVE);
        user.setPhone("+375291234567");

        User saveActive = userRepository.save(user);
        Role saveRole = roleRepository.save(role);

        Exception exception = assertThrows(InvalidException.class, () -> {
            userService.isActive(saveActive);
        });

        String expectedMessage = "User is not active";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void isActiveFalseDeleted() {
        User user = new User();

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("USER");
        roles.add(role);

        user.setFirstName("userFirstName");
        user.setLastName("userLastName");
        user.setUsername("userUsername");
        user.setPassword("userPassword");
        user.setRoles(roles);
        role.setUser(user);
        user.setPassword("userPassword");
        user.setEmail("user@gmail.com");
        user.setStatus(Status.DELETED);
        user.setPhone("+375291234567");

        User saveActive = userRepository.save(user);
        Role saveRole = roleRepository.save(role);

        Exception exception = assertThrows(InvalidException.class, () -> {
            userService.isActive(saveActive);
        });

        String expectedMessage = "User is not active";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
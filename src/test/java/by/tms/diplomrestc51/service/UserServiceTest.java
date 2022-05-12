package by.tms.diplomrestc51.service;

import by.tms.diplomrestc51.dto.UserDTO;
import by.tms.diplomrestc51.entity.Role;
import by.tms.diplomrestc51.entity.User;
import by.tms.diplomrestc51.enums.Status;
import by.tms.diplomrestc51.exception.InvalidException;
import by.tms.diplomrestc51.repository.RoleRepository;
import by.tms.diplomrestc51.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
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

    @Test
    void deleteUser() {
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
        userService.deleteUser(saveActive);

        assertEquals(Status.DELETED, saveActive.getStatus());
    }

    @Test
    void existByEmailTrue() {
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

        assertTrue(userService.existByEmail(saveActive.getEmail()));
    }

    @Test
    void existByEmailFalse() {
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

        assertFalse(userService.existByEmail("user" + saveActive.getEmail()));
    }

    @Test
    void existByUsernameTrue() {
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

        assertTrue(userService.existByUsername(saveActive.getUsername()));
    }

    @Test
    void existByUsernameFalse() {
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

        assertFalse(userService.existByUsername("user" + saveActive.getUsername()));
    }

    @Test
    void registration() {

        UserDTO userDto = new UserDTO();
        userDto.setFirstName("userFirstName1");
        userDto.setLastName("userLastName1");
        userDto.setUsername("userUsername1");
        userDto.setPassword("userPassword1");
        userDto.setEmail("user@gmail.com1");
        userDto.setPhone("+3752912345671");

        userService.registration(userDto);

        User user = userRepository.findByUsername(userDto.getUsername()).get();

        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertFalse(user.getPassword().equals(userDto.getPassword()));
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPhone(), user.getPhone());
        assertEquals(Status.ACTIVE, user.getStatus());
        assertEquals("USER", user.getRoles().get(0).getName());
    }
}
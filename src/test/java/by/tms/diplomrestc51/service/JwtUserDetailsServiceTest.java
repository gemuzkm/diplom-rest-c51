package by.tms.diplomrestc51.service;

import by.tms.diplomrestc51.entity.Role;
import by.tms.diplomrestc51.entity.User;
import by.tms.diplomrestc51.enums.Status;
import by.tms.diplomrestc51.exception.InvalidException;
import by.tms.diplomrestc51.repository.RoleRepository;
import by.tms.diplomrestc51.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUserDetailsServiceTest {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    void loadUserByUsernameFalse() {

        String username = "testUser";

        Exception exception = assertThrows(RuntimeException.class, () -> {
            jwtUserDetailsService.loadUserByUsername(username);
        });

        String expectedMessage = "User with username: " + username + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void loadUserByUsernameTrue() {
        User user = new User();

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("USER");
        roles.add(role);

        user.setFirstName("userFirstName");
        user.setLastName("userLastName");
        user.setUsername("userUsernameJWT");
        user.setPassword("userPassword");
        user.setRoles(roles);
        role.setUser(user);
        user.setPassword("userPassword");
        user.setEmail("user@gmail.com");
        user.setStatus(Status.ACTIVE);
        user.setPhone("+375291234567");

        User saveActive = userRepository.save(user);
        Role saveRole = roleRepository.save(role);

        assertNotNull(jwtUserDetailsService.loadUserByUsername("userUsernameJWT"));
    }
}
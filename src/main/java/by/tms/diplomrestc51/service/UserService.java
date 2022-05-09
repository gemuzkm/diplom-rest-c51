package by.tms.diplomrestc51.service;

import by.tms.diplomrestc51.dto.UserDTO;
import by.tms.diplomrestc51.entity.role.Role;
import by.tms.diplomrestc51.entity.user.User;
import by.tms.diplomrestc51.enums.Status;
import by.tms.diplomrestc51.exception.InvalidException;
import by.tms.diplomrestc51.mapper.UserMapper;
import by.tms.diplomrestc51.repository.RoleRepository;
import by.tms.diplomrestc51.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       UserMapper userMapper,
                       @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void registration(UserDTO userDTO) {
        User user = userMapper.userDtoToUser(userDTO);
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setStatus(Status.ACTIVE);
        role.setName("USER");
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        role.setUser(user);
        User saveUser = userRepository.save(user);
        Role saveRole = roleRepository.save(role);

        log.info("IN register - user: {} successfully registered", saveUser);
    }

    public User findByUsername(String username) {
        User byUsername = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username: " + username + " not found"));
        return byUsername;
    }

    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public String getAuthenticationUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public User getAuthenticationUser() {
        return userRepository.findByUsername(getAuthenticationUserName()).get();
    }

    public void deleteUser(User user) {
        user.setStatus(Status.DELETED);
        User deleted = userRepository.save(user);

        log.info("IN deleteUser - user: {} successfully deleted", deleted);
    }

    public boolean isAdmin() {
        if (getAuthenticationUser().getRoles().equals("ADMIN")) {
            return true;
        } else {
            throw new InvalidException("User is not admin");
        }
    }

    public boolean isUser() {
        if (getAuthenticationUser().getRoles().equals("USER")) {
            return true;
        } else {
            throw new InvalidException("User is not user");
        }
    }

    public boolean isActive(User user) {
        if (user.getStatus().equals("ACTIVE")) {
            return true;
        } else {
            throw new InvalidException("User is not active");
        }
    }
}

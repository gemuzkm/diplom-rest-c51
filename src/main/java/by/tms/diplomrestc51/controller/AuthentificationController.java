package by.tms.diplomrestc51.controller;

import by.tms.diplomrestc51.configuration.security.jwt.JwtTokenProvider;
import by.tms.diplomrestc51.dto.AuthRequestDTO;
import by.tms.diplomrestc51.dto.UserDTO;
import by.tms.diplomrestc51.entity.user.User;
import by.tms.diplomrestc51.exception.InvalidException;
import by.tms.diplomrestc51.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "Authentification", description = "Authentication of users")
@RequestMapping("/api/v1/auth")
public class AuthentificationController {
    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthentificationController(UserService service, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Authorization. Receiving Token.")
    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> logIn(@Valid
                                                     @ApiParam(value = "Authorization, via username and password")
                                                     @RequestBody AuthRequestDTO requestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));
        User user = service.findByUsername(requestDto.getUsername());

        String token = jwtTokenProvider.generateToken(requestDto.getUsername(), user.getRoles());

        Map<Object, Object> resp = new HashMap<>();
        resp.put("username", requestDto.getUsername());
        resp.put("token", token);

        log.info("User - {} - logged in", requestDto.getUsername());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "New user registration")
    @PostMapping("/reg")
    public ResponseEntity<UserDTO> registration(@Valid
                                                    @ApiParam(value = "New user registration with verification of all datad")
                                                    @RequestBody UserDTO userDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors() | service.existByUsername(userDTO.getUsername()) | service.existByEmail(userDTO.getEmail())) {
            throw new InvalidException();
        }
        service.registration(userDTO);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
    })
    @ApiOperation(value = "Ending a user session")
    @GetMapping("/logout")
    public ResponseEntity<Map<Object, Object>> logOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<Object, Object> resp = new HashMap<>();

        if (auth != null) {
            resp.put("username", auth.getName());
            resp.put("session, lastAccessedTime", session.getLastAccessedTime());
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        log.info("User - {} - logged out", auth.getName());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}

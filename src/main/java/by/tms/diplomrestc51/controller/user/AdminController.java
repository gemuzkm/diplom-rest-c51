package by.tms.diplomrestc51.controller.user;

import by.tms.diplomrestc51.entity.user.User;
import by.tms.diplomrestc51.exception.InvalidException;
import by.tms.diplomrestc51.exception.NotFoundException;
import by.tms.diplomrestc51.repository.UserRepository;
import by.tms.diplomrestc51.service.UserService;
import by.tms.diplomrestc51.validation.IdValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = "Admin", description = "Operations with users")
@RequestMapping("/api/v1/admin")
public class AdminController {

    private IdValidation idValidation;
    private final UserRepository userRepository;
    private final UserService userService;

    public AdminController(UserRepository userRepository,
                           UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @ApiOperation(value = "Get user by user name", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<User> getUserByUsername(@ApiParam(value = "The name that needs to be fetched.", example = "username")
                                    @PathVariable("username") String username) {

        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException();
        }

        User getUser = userRepository.findByUsername(username).get();
        return ResponseEntity.ok(getUser);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Updated user", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/user/{id}", produces = "application/json")
    public ResponseEntity<User> getUserById(@ApiParam(value = "The id that needs to be fetched", example = "1")
                                            @PathVariable("id") Long id) {

        idValidation.validate(id);

        if (userRepository.findById(id).isPresent()) {
            User getUser = userRepository.findById(id).get();
            return ResponseEntity.ok(getUser);
        } else {
            throw new NotFoundException();
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Updated user", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<User> update(@ApiParam(value = "username that need to be updated", example = "username")
                                       @PathVariable("username") String username,
                                       @ApiParam(value = "Updated user object", example = "user")
                                       @Valid @RequestBody User user,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException();
        }

        User update = userRepository.findByUsername(username).get();
        user.setId(update.getId());
        userRepository.save(user);

        return ResponseEntity.ok(update);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @ApiOperation(value = "Deleting a user by username", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{username}", produces = "application/json")
    public void deleteUser(@ApiParam(value = "Username that need to be deleted", example = "username")
                           @PathVariable("username") String username) {

        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException();
        }

        User user = userRepository.findByUsername(username).get();
        userService.deleteUser(user);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Deleting a user by username", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/user/{id}", produces = "application/json")
    public void deleteUser(@ApiParam(value = "User ID for deletion", example = "1")
                           @PathVariable("id") Long id) {

        idValidation.validate(id);

        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            userService.deleteUser(user);
        } else {
            throw new NotFoundException();
        }
    }
}

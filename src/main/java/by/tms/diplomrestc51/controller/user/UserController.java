package by.tms.diplomrestc51.controller.user;

import by.tms.diplomrestc51.entity.Device;
import by.tms.diplomrestc51.entity.user.User;
import by.tms.diplomrestc51.exception.ForbiddenException;
import by.tms.diplomrestc51.exception.InvalidException;
import by.tms.diplomrestc51.exception.NotFoundException;
import by.tms.diplomrestc51.repository.DeviceRepository;
import by.tms.diplomrestc51.repository.UserRepository;
import by.tms.diplomrestc51.service.UserService;
import by.tms.diplomrestc51.validation.IdValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Api(tags = "User", description = "Operations on the user")
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private IdValidation idValidation;
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @ApiOperation(value = "Get user by user name", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(produces = "application/json")
    public ResponseEntity<User> get(@ApiParam(value = "The name that needs to be fetched.", example = "username")
                                    @PathVariable("username") String username) {


        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException();
        }

        if (userService.getAuthenticationUserName().equals(username)) {
            User getUser = userRepository.findByUsername(username).get();
            return ResponseEntity.ok(getUser);
        } else {
            throw new ForbiddenException();
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @ApiOperation(value = "Updated user", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<User> update(@ApiParam(value = "username that need to be updated", example = "username")
                                       @PathVariable("username") String username,
                                       @ApiParam(value = "Updated user object", example = "user") @Valid @RequestBody User user,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException();
        }

        if (userService.getAuthenticationUserName().equals(username)) {
            User update = userRepository.findByUsername(username).get();
            user.setId(update.getId());
            userRepository.save(user);

            return ResponseEntity.ok(update);
        } else {
            throw new ForbiddenException();
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @ApiOperation(value = "Delete user", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{username}", produces = "application/json")
    public void deleteUser(@ApiParam(value = "username that need to be deleted", example = "username")
                           @PathVariable("username") String username) {

        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException();
        }

        if (userService.getAuthenticationUserName().equals(username)) {
            User user = userRepository.findByUsername(username).get();
            userService.deleteUser(user);
        } else {
            throw new ForbiddenException();
        }
    }


    @GetMapping(value = "/devices", produces = "application/json")
    public ResponseEntity<List<Device>> getDevices() {
        Optional<List<Device>> allByUsername = deviceRepository.findAllByUsername(userService.getAuthenticationUserName());
        return ResponseEntity.ok(allByUsername.get());
    }

    @GetMapping(value = "/devices/{id}", produces = "application/json")
    public ResponseEntity<Device> getDevice(@PathVariable("id") Long id) {
        if (id < 1) {
            throw new InvalidException();
        }

        Optional<Device> byId = deviceRepository.findById(id);




        return ResponseEntity.ok(deviceRepository.findById(id).get());
    }

    @PostMapping(value = "/devices", produces = "application/json")
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device device, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }
        return ResponseEntity.ok(deviceRepository.save(device));
    }

    @PutMapping(value = "/devices/{id}", produces = "application/json")
    public ResponseEntity<Device> updateDevice(@PathVariable("id") Long id, @Valid @RequestBody Device device, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }
        if (deviceRepository.findById(id).isPresent()) {
            Device update = deviceRepository.findById(id).get();
            device.setId(update.getId());
            return ResponseEntity.ok(deviceRepository.save(device));
        } else {
            throw new NotFoundException();
        }
    }

    @DeleteMapping(value = "/devices/{id}", produces = "application/json")
    public void deleteDevice(@PathVariable("id") Long id) {
        if (deviceRepository.findById(id).isPresent()) {
            deviceRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    @GetMapping(value = "/devices/{id}/readings", produces = "application/json")
    public ResponseEntity<List<Reading>> getReadings(@PathVariable("id") Long id) {
        if (deviceRepository.findById(id).isPresent()) {
            return ResponseEntity.ok(readingRepository.findByDeviceId(id));
        } else {
}

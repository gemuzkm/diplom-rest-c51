package by.tms.diplomrestc51.controller.user;

import by.tms.diplomrestc51.entity.Device;
import by.tms.diplomrestc51.entity.user.User;
import by.tms.diplomrestc51.enums.Status;
import by.tms.diplomrestc51.enums.TypeDevice;
import by.tms.diplomrestc51.exception.ExistsException;
import by.tms.diplomrestc51.exception.InvalidException;
import by.tms.diplomrestc51.exception.NotFoundException;
import by.tms.diplomrestc51.repository.DeviceRepository;
import by.tms.diplomrestc51.repository.UserRepository;
import by.tms.diplomrestc51.service.DeviceService;
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
@Api(tags = "Admin", description = "Operations with users")
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final DeviceRepository deviceRepository;
    private final DeviceService deviceService;
    private IdValidation idValidation;
    private final UserRepository userRepository;
    private final UserService userService;

    public AdminController(UserRepository userRepository,
                           UserService userService,
                           DeviceRepository deviceRepository,
                           DeviceService deviceService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.deviceRepository = deviceRepository;
        this.deviceService = deviceService;
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
    public ResponseEntity<User> update(@ApiParam(value = "Username that need to be updated", example = "username")
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
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Updated user by User ID", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<User> update(@ApiParam(value = "User ID for updating", example = "1")
                                       @PathVariable("userId") Long userId,
                                       @ApiParam(value = "Updated user object", example = "user")
                                       @Valid @RequestBody User user,
                                       BindingResult bindingResult) {

        idValidation.validate(userId);

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        if (userRepository.findById(userId).isPresent()) {
            User update = userRepository.findById(userId).get();
            user.setId(update.getId());
            userRepository.save(user);

            return ResponseEntity.ok(update);
        } else {
            throw new NotFoundException();
        }
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @ApiOperation(value = "List of supported devices", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/devices/supported", produces = "application/json")
    public ResponseEntity<?> getSupportedDevices() {
        return ResponseEntity.ok(TypeDevice.values());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Get a list of devices by user ID", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/devices/{userId}", produces = "application/json")
    public ResponseEntity<List<Device>> getDevices(@ApiParam(value = "User ID for devices", example = "1")
                                                   @PathVariable("userId") Long userId) {
        idValidation.validate(userId);

        if (userRepository.findById(userId).isPresent()) {
            User user = userRepository.findById(userId).get();
            Optional<List<Device>> allByUsername = deviceRepository.findAllByUser(user);
            return ResponseEntity.ok(allByUsername.get());
        } else {
            throw new NotFoundException();
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Device not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Get user device by ID", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/device/{id}", produces = "application/json")
    public ResponseEntity<Device> getDevice(@ApiParam(value = "The ID of the device you want to get information about", example = "1")
                                            @PathVariable("id") Long id) {
        IdValidation.validate(id);

        if (deviceRepository.findById(id).isPresent()) {
            Optional<Device> DeviceById = deviceRepository.findById(id);
            return ResponseEntity.ok(DeviceById.get());
        } else {
            throw new NotFoundException();
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Device already exists"),
    })
    @ApiOperation(value = "Update device data", notes = "Updating basic device data", authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/device/{id}", produces = "application/json")
    public ResponseEntity<Device> updateDevice(@ApiParam(value = "The ID of the device to be updated", example = "1")
                                               @PathVariable("id") Long id,
                                               @ApiParam(value = "Basic description of the device with updated data", example = "Device")
                                               @Valid @RequestBody Device device, BindingResult bindingResult) {
        IdValidation.validate(id);

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        Device deviceToUpdate = deviceRepository.findById(id).orElseThrow(InvalidException::new);

        if (deviceService.existBySerialNumber(device.getSerialNumber()) | deviceService.exitsByMacAddress(device.getMacAddress())) {
            throw new ExistsException();
        }

        deviceToUpdate.setModel(device.getModel());
        deviceToUpdate.setSerialNumber(device.getSerialNumber());
        deviceToUpdate.setMacAddress(device.getMacAddress());
        deviceToUpdate.setFirmwareVersion(device.getFirmwareVersion());
        deviceToUpdate.setIpAddress(device.getIpAddress());
        deviceToUpdate.setStatus(device.getStatus());

        Device save = deviceRepository.save(deviceToUpdate);
        return ResponseEntity.ok(save);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Device not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Removing a device", notes = "Removing a device from a user", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/device/{id}", produces = "application/json")
    public void deleteDevice(@ApiParam(value = "Enter the device ID to delete", example = "1")
                             @PathVariable("id") Long id) {
        IdValidation.validate(id);

        if (deviceRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        } else {
            Device device = deviceRepository.findById(id).get();
            device.setStatus(Status.DELETED);
            deviceRepository.save(device);
        }
    }
}

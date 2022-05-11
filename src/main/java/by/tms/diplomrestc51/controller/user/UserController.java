package by.tms.diplomrestc51.controller.user;

import by.tms.diplomrestc51.dto.ParameterDTO;
import by.tms.diplomrestc51.entity.Device;
import by.tms.diplomrestc51.entity.Parameter;
import by.tms.diplomrestc51.entity.ParameterValues;
import by.tms.diplomrestc51.entity.User;
import by.tms.diplomrestc51.enums.Status;
import by.tms.diplomrestc51.enums.TypeDevice;
import by.tms.diplomrestc51.enums.TypeParameter;
import by.tms.diplomrestc51.exception.ExistsException;
import by.tms.diplomrestc51.exception.ForbiddenException;
import by.tms.diplomrestc51.exception.InvalidException;
import by.tms.diplomrestc51.exception.NotFoundException;
import by.tms.diplomrestc51.repository.DeviceRepository;
import by.tms.diplomrestc51.repository.ParameterRepository;
import by.tms.diplomrestc51.repository.ParameterValuesRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@Api(tags = "User", description = "Operations on the user")
@RequestMapping("/api/v1/user")
public class UserController {
    private final ParameterRepository parameterRepository;
    private final ParameterValuesRepository parameterValuesRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceService deviceService;
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository,
                          UserService userService,
                          DeviceRepository deviceRepository,
                          DeviceService deviceService,
                          ParameterRepository parameterRepository,
                          ParameterValuesRepository parameterValuesRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.deviceRepository = deviceRepository;
        this.deviceService = deviceService;
        this.parameterRepository = parameterRepository;
        this.parameterValuesRepository = parameterValuesRepository;
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
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
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
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @ApiOperation(value = "List of user devices", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/devices", produces = "application/json")
    public ResponseEntity<List<Device>> getDevices() {
        Optional<List<Device>> allByUsername = deviceRepository.findAllByUser(userService.getAuthenticationUser());
        return ResponseEntity.ok(allByUsername.get());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Device not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Get user device by ID", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/device/{id}", produces = "application/json")
    public ResponseEntity<Device> getDevice(@ApiParam(value = "The ID of the device you want to get information about", example = "1")
                                            @PathVariable("id") Long id) {
        IdValidation.validate(id);

        Optional<Device> DeviceById = deviceRepository.findById(id);

        if (DeviceById.get().getUser().getUsername().equals(userService.getAuthenticationUserName())) {
            return ResponseEntity.ok(DeviceById.get());
        } else {
            throw new InvalidException();
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "The device does not exist"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Add device to user", notes = "This can only be done by the logged in user", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/device", produces = "application/json")
    public ResponseEntity<Device> createDevice(@Valid
                                               @ApiParam(value = "A device with a basic description is added", example = "Device")
                                               @RequestBody Device device, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        if (deviceService.existBySerialNumber(device.getSerialNumber()) | deviceService.exitsByMacAddress(device.getMacAddress())) {
            throw new ExistsException();
        }

        device.setStatus(Status.ACTIVE);
        device.setUser(userService.getAuthenticationUser());

        if (Arrays.stream(TypeDevice.values()).anyMatch((typeDevice -> typeDevice.name().equals(device.getTypeDevice().name())))) {
            return ResponseEntity.ok(deviceRepository.save(device));
        } else {
            throw new ExistsException();
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

        if (deviceToUpdate.getUser().getId() != userService.getAuthenticationUser().getId()) {
            throw new InvalidException();
        }

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
            @ApiResponse(responseCode = "403", description = "Forbidden"),
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
        } else if (deviceRepository.findById(id).get().getUser().getId() != userService.getAuthenticationUser().getId()) {
            throw new ForbiddenException();
        } else {
            Device device = deviceRepository.findById(id).get();
            device.setStatus(Status.DELETED);
            deviceRepository.save(device);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Device not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Add parameter by device", notes = "", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/device/parameter/{id}", produces = "application/json")
    public void addParameter(@ApiParam(value = "Enter the device ID to add the parameter", example = "1") @PathVariable("id") Long id,
                             @ApiParam(value = "Input parameters", example = "ParameterDTO")
                             @Valid @RequestBody ParameterDTO parameterDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        IdValidation.validate(id);

        if (deviceRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }

        Device device = deviceRepository.findById(id).get();

        if (device.getUser().getId() != userService.getAuthenticationUser().getId()) {
            throw new ForbiddenException();
        }

        List<String> supportedTypeParameters = deviceService.getSupportedTypeParameters(device);

        if (supportedTypeParameters.contains(parameterDTO.getTypeParameter().toString())) {
            Parameter parameter = new Parameter();
            parameter.setType(parameterDTO.getTypeParameter());
            parameter.setDevice(device);

            ParameterValues parameterValues = new ParameterValues();
            parameterValues.setValue(parameterDTO.getValue());
            parameterValues.setParameter(parameter);
            parameterValuesRepository.save(parameterValues);

            parameterRepository.save(parameter);
        } else {
            throw new InvalidException();
        }
    }
}

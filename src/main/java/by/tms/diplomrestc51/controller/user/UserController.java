package by.tms.diplomrestc51.controller.user;

import by.tms.diplomrestc51.entity.Device;
import by.tms.diplomrestc51.entity.device.*;
import by.tms.diplomrestc51.entity.user.User;
import by.tms.diplomrestc51.enums.Status;
import by.tms.diplomrestc51.enums.TypeDevice;
import by.tms.diplomrestc51.exception.ExistsException;
import by.tms.diplomrestc51.exception.ForbiddenException;
import by.tms.diplomrestc51.exception.InvalidException;
import by.tms.diplomrestc51.exception.NotFoundException;
import by.tms.diplomrestc51.mapper.DeviceMapper;
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
import org.springframework.http.HttpStatus;
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

    private final DeviceRepository deviceRepository;
    private final DeviceService deviceService;
    private final DeviceMapper deviceMapper;
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository,
                          UserService userService,
                          DeviceRepository deviceRepository,
                          DeviceService deviceService,
                          DeviceMapper deviceMapper) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.deviceRepository = deviceRepository;
        this.deviceService = deviceService;
        this.deviceMapper = deviceMapper;
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
    public ResponseEntity<?> createDevice(@Valid
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

        if (device.getTypeDevice().equals(TypeDevice.WASHER)) {
            WasherDevice washerDevice = deviceMapper.deviceToWasherDevice(device);
            WasherDevice saveWasher = deviceRepository.save(washerDevice);

//            System.out.println(saveWasher.getParameters());

            return ResponseEntity.ok(saveWasher);
        }

        if (device.getTypeDevice().equals(TypeDevice.REFRIGERATOR)) {
            RefrigeratorDevice refrigeratorDevice = deviceMapper.deviceToRefrigeratorDevice(device);
            RefrigeratorDevice saveRefrigerator = deviceRepository.save(refrigeratorDevice);

            return ResponseEntity.ok(saveRefrigerator);
        }

        if (device.getTypeDevice().equals(TypeDevice.VACUUM_CLEANER)) {
            VacuumCleanerDevice vacuumCleanerDevice = deviceMapper.deviceToVacuumCleanerDevice(device);
            VacuumCleanerDevice saveVacuumCleaner = deviceRepository.save(vacuumCleanerDevice);

            return ResponseEntity.ok(saveVacuumCleaner);
        }

        if (device.getTypeDevice().equals(TypeDevice.SMART_LAMP)) {
            SmartLampDevice smartLampDevice = deviceMapper.deviceToSmartLampDevice(device);
            SmartLampDevice saveSmartLamp = deviceRepository.save(smartLampDevice);

            return ResponseEntity.ok(saveSmartLamp);
        }

        if (device.getTypeDevice().equals(TypeDevice.HUMIDITY_SENSOR)) {
            HumiditySensorDevice humiditySensorDevice = deviceMapper.deviceToHumiditySensorDevice(device);
            HumiditySensorDevice saveHumiditySensor = deviceRepository.save(humiditySensorDevice);

            return ResponseEntity.ok(saveHumiditySensor);
        }

        if (device.getTypeDevice().equals(TypeDevice.TEMPERATURE_SENSOR)) {
            TemperatureSensorDevice temperatureSensorDevice = deviceMapper.deviceToTemperatureSensorDevice(device);
            TemperatureSensorDevice saveTemperatureSensor = deviceRepository.save(temperatureSensorDevice);

            return ResponseEntity.ok(saveTemperatureSensor);
        }

        if (device.getTypeDevice().equals(TypeDevice.SMART_PLUG)) {
            SmartPlugDevice smartPlugDevice = deviceMapper.deviceToSmartPlugDevice(device);
            SmartPlugDevice saveSmartPlug = deviceRepository.save(smartPlugDevice);

            return ResponseEntity.ok(saveSmartPlug);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
}

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

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceMapper deviceMapper;

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

    @GetMapping(value = "/devices/supported", produces = "application/json")
    public ResponseEntity<?> getSupportedDevices() {
        return ResponseEntity.ok(TypeDevice.values());
    }

    @GetMapping(value = "/devices", produces = "application/json")
    public ResponseEntity<List<Device>> getDevices() {
        Optional<List<Device>> allByUsername = deviceRepository.findAllByUser(userService.getAuthenticationUser());
        return ResponseEntity.ok(allByUsername.get());
    }

    @GetMapping(value = "/devices/{id}", produces = "application/json")
    public ResponseEntity<Device> getDevice(@PathVariable("id") Long id) {
        IdValidation.validate(id);

        Optional<Device> DeviceById = deviceRepository.findById(id);

        if (DeviceById.get().getUser().getUsername().equals(userService.getAuthenticationUserName())) {
            return ResponseEntity.ok(DeviceById.get());
        } else {
            throw new InvalidException();
        }
    }

    @PostMapping(value = "/devices", produces = "application/json")
    public ResponseEntity<?> createDevice(@Valid @RequestBody Device device, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        if (deviceService.existBySerialNumber(device.getSerialNumber()) | deviceService.exitsByMacAddress(device.getMacAddress())) {
            throw new ExistsException();
        }

        if (device.getTypeDevice().equals(TypeDevice.WASHER)) {
            WasherDevice washerDevice = deviceMapper.deviceToWasherDevice(device);

            if (washerDevice.getStatus() == null) {
                washerDevice.setStatus(Status.ACTIVE);
            }

            if (washerDevice.getUser() == null) {
                washerDevice.setUser(userService.getAuthenticationUser());
            }

            WasherDevice saveWasher = deviceRepository.save(washerDevice);

            return ResponseEntity.ok(saveWasher);
        }

        if (device.getTypeDevice().equals(TypeDevice.REFRIGERATOR)) {
            RefrigeratorDevice refrigeratorDevice = deviceMapper.deviceToRefrigeratorDevice(device);

            if (refrigeratorDevice.getStatus() == null) {
                refrigeratorDevice.setStatus(Status.ACTIVE);
            }

            if (refrigeratorDevice.getUser() == null) {
                refrigeratorDevice.setUser(userService.getAuthenticationUser());
            }

            RefrigeratorDevice saveRefrigerator = deviceRepository.save(refrigeratorDevice);

            return ResponseEntity.ok(saveRefrigerator);
        }

        if (device.getTypeDevice().equals(TypeDevice.VACUUM_CLEANER)) {
            VacuumCleanerDevice vacuumCleanerDevice = deviceMapper.deviceToVacuumCleanerDevice(device);

            if (vacuumCleanerDevice.getStatus() == null) {
                vacuumCleanerDevice.setStatus(Status.ACTIVE);
            }

            if (vacuumCleanerDevice.getUser() == null) {
                vacuumCleanerDevice.setUser(userService.getAuthenticationUser());
            }

            VacuumCleanerDevice saveVacuumCleaner = deviceRepository.save(vacuumCleanerDevice);

            return ResponseEntity.ok(saveVacuumCleaner);
        }

        if (device.getTypeDevice().equals(TypeDevice.SMART_LAMP)) {
            SmartLampDevice smartLampDevice = deviceMapper.deviceToSmartLampDevice(device);

            if (smartLampDevice.getStatus() == null) {
                smartLampDevice.setStatus(Status.ACTIVE);
            }

            if (smartLampDevice.getUser() == null) {
                smartLampDevice.setUser(userService.getAuthenticationUser());
            }

            SmartLampDevice saveSmartLamp = deviceRepository.save(smartLampDevice);

            return ResponseEntity.ok(saveSmartLamp);
        }

        if (device.getTypeDevice().equals(TypeDevice.HUMIDITY_SENSOR)) {
            HumiditySensorDevice humiditySensorDevice = deviceMapper.deviceToHumiditySensorDevice(device);

            if (humiditySensorDevice.getStatus() == null) {
                humiditySensorDevice.setStatus(Status.ACTIVE);
            }

            if (humiditySensorDevice.getUser() == null) {
                humiditySensorDevice.setUser(userService.getAuthenticationUser());
            }

            HumiditySensorDevice saveHumiditySensor = deviceRepository.save(humiditySensorDevice);

            return ResponseEntity.ok(saveHumiditySensor);
        }

        if (device.getTypeDevice().equals(TypeDevice.TEMPERATURE_SENSOR)) {
            TemperatureSensorDevice temperatureSensorDevice = deviceMapper.deviceToTemperatureSensorDevice(device);

            if (temperatureSensorDevice.getStatus() == null) {
                temperatureSensorDevice.setStatus(Status.ACTIVE);
            }

            if (temperatureSensorDevice.getUser() == null) {
                temperatureSensorDevice.setUser(userService.getAuthenticationUser());
            }

            TemperatureSensorDevice saveTemperatureSensor = deviceRepository.save(temperatureSensorDevice);

            return ResponseEntity.ok(saveTemperatureSensor);
        }

        if (device.getTypeDevice().equals(TypeDevice.SMART_PLUG)) {
            SmartPlugDevice smartPlugDevice = deviceMapper.deviceToSmartPlugDevice(device);

            if (smartPlugDevice.getStatus() == null) {
                smartPlugDevice.setStatus(Status.ACTIVE);
            }

            if (smartPlugDevice.getUser() == null) {
                smartPlugDevice.setUser(userService.getAuthenticationUser());
            }

            SmartPlugDevice saveSmartPlug = deviceRepository.save(smartPlugDevice);

            return ResponseEntity.ok(saveSmartPlug);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/devices/{id}", produces = "application/json")
    public ResponseEntity<Device> updateDevice(@PathVariable("id") Long id, @Valid @RequestBody Device device, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidException();
        }

        if (deviceService.existBySerialNumber(device.getSerialNumber()) | deviceService.exitsByMacAddress(device.getMacAddress())) {
            throw new ExistsException();
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
        IdValidation.validate(id);

        if(deviceRepository.findById(id).isEmpty()) {
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

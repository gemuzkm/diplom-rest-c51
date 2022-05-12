package by.tms.diplomrestc51.service;

import by.tms.diplomrestc51.entity.Device;
import by.tms.diplomrestc51.enums.Status;
import by.tms.diplomrestc51.enums.TypeDevice;
import by.tms.diplomrestc51.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeviceServiceTest {
    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceService deviceService;

    @Test
    void existBySerialNumberTrue() {
        Device device = new Device();
        device.setSerialNumber("123456789");
        device.setFirmwareVersion("1.0");
        device.setIpAddress("127.0.0.1");
        device.setMacAddress("00:00:00:00:00:00");
        device.setStatus(Status.ACTIVE);
        device.setTypeDevice(TypeDevice.WASHER);
        device.setDescription("Test");
        device.setModel("Test");
        Device saveDevice = deviceRepository.save(device);

        assertTrue(deviceService.existBySerialNumber(saveDevice.getSerialNumber()));

    }

    @Test
    void existBySerialNumberFalse() {
        Device device = new Device();
        device.setSerialNumber("123456789");
        device.setFirmwareVersion("1.0");
        device.setIpAddress("127.0.0.1");
        device.setMacAddress("00:00:00:00:00:00");
        device.setStatus(Status.ACTIVE);
        device.setTypeDevice(TypeDevice.WASHER);
        device.setDescription("Test");
        device.setModel("Test");
        Device saveDevice = deviceRepository.save(device);

        assertFalse(deviceService.existBySerialNumber("987654321"));
    }

    @Test
    void exitsByMacAddressTrue() {
        Device device = new Device();
        device.setSerialNumber("123456789");
        device.setFirmwareVersion("1.0");
        device.setIpAddress("127.0.0.1");
        device.setMacAddress("00:00:00:00:00:00");
        device.setStatus(Status.ACTIVE);
        device.setTypeDevice(TypeDevice.WASHER);
        device.setDescription("Test");
        device.setModel("Test");
        Device saveDevice = deviceRepository.save(device);

        assertTrue(deviceService.exitsByMacAddress(saveDevice.getMacAddress()));
    }

    @Test
    void exitsByMacAddressFalse() {
        Device device = new Device();
        device.setSerialNumber("123456789");
        device.setFirmwareVersion("1.0");
        device.setIpAddress("127.0.0.1");
        device.setMacAddress("00:00:00:00:00:00");
        device.setStatus(Status.ACTIVE);
        device.setTypeDevice(TypeDevice.WASHER);
        device.setDescription("Test");
        device.setModel("Test");
        Device saveDevice = deviceRepository.save(device);

        assertFalse(deviceService.exitsByMacAddress("99:99:99:99:99:99"));
    }
}
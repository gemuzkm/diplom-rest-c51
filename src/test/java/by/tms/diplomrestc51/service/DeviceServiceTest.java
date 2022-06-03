package by.tms.diplomrestc51.service;

import by.tms.diplomrestc51.entity.Device;
import by.tms.diplomrestc51.enums.Status;
import by.tms.diplomrestc51.enums.TypeDevice;
import by.tms.diplomrestc51.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
        if (deviceRepository.findByMacAddress("00:00:00:00:00:00").isEmpty()) {
            Device device = new Device();
            device.setSerialNumber("123456789");
            device.setFirmwareVersion("1.0");
            device.setIpAddress("127.0.0.1");
            device.setMacAddress("00:00:00:00:00:00");
            device.setStatus(Status.ACTIVE);
            device.setTypeDevice(TypeDevice.WASHER);
            device.setDescription("Test");
            device.setModel("Test");
            deviceRepository.save(device);
        }

        assertTrue(deviceService.existBySerialNumber("123456789"));
    }

    @Test
    void existBySerialNumberFalse() {
        if(deviceRepository.findByMacAddress("00:00:00:00:00:00").isEmpty()) {
            Device device = new Device();
            device.setSerialNumber("123456789");
            device.setFirmwareVersion("1.0");
            device.setIpAddress("127.0.0.1");
            device.setMacAddress("00:00:00:00:00:00");
            device.setStatus(Status.ACTIVE);
            device.setTypeDevice(TypeDevice.WASHER);
            device.setDescription("Test");
            device.setModel("Test");
            deviceRepository.save(device);
        }

        assertFalse(deviceService.existBySerialNumber("987654321"));
    }

    @Test
    void exitsByMacAddressTrue() {
        if (deviceRepository.findByMacAddress("00:00:00:00:00:00").isEmpty()) {
            Device device = new Device();
            device.setSerialNumber("123456789");
            device.setFirmwareVersion("1.0");
            device.setIpAddress("127.0.0.1");
            device.setMacAddress("00:00:00:00:00:00");
            device.setStatus(Status.ACTIVE);
            device.setTypeDevice(TypeDevice.WASHER);
            device.setDescription("Test");
            device.setModel("Test");
            deviceRepository.save(device);
        }

        assertTrue(deviceService.exitsByMacAddress("00:00:00:00:00:00"));
    }

    @Test
    void exitsByMacAddressFalse() {
        if (deviceRepository.findByMacAddress("00:00:00:00:00:00").isEmpty()) {
            Device device = new Device();
            device.setSerialNumber("123456789");
            device.setFirmwareVersion("1.0");
            device.setIpAddress("127.0.0.1");
            device.setMacAddress("00:00:00:00:00:00");
            device.setStatus(Status.ACTIVE);
            device.setTypeDevice(TypeDevice.WASHER);
            device.setDescription("Test");
            device.setModel("Test");
            deviceRepository.save(device);
        }

        assertFalse(deviceService.exitsByMacAddress("99:99:99:99:99:99"));
    }
}
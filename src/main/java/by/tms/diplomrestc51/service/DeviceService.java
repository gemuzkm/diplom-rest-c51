package by.tms.diplomrestc51.service;

import by.tms.diplomrestc51.entity.Device;
import by.tms.diplomrestc51.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public boolean existBySerialNumber(String serialNumber) {
        Optional<String> optional = deviceRepository.findBySerialNumber(serialNumber);
        return optional.isPresent();
    }

    public boolean exitsByMacAddress(String macAddress) {
        Optional<String> optional = deviceRepository.findByMacAddress(macAddress);
        return optional.isPresent();
    }

    public List<String> getSupportedTypeParameters(Device device) {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            properties.load(classLoader.getResourceAsStream("device.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String parametersDevice = properties.getProperty(device.getTypeDevice().toString());
        return Arrays.asList(parametersDevice.split(","));
    }
}

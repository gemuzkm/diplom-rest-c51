package by.tms.diplomrestc51.service;

import by.tms.diplomrestc51.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@PropertySource("classpath:device.properties")
@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    @Value("#{'${washerDeviceParameters}'.split(',')}")
    List<String> washerDeviceParameters;

    public boolean existBySerialNumber(String serialNumber) {
        Optional<String> optional = deviceRepository.findBySerialNumber(serialNumber);
        return optional.isPresent();
    }

    public boolean exitsByMacAddress(String macAddress) {
        Optional<String> optional = deviceRepository.findByMacAddress(macAddress);
        return optional.isPresent();
    }
}

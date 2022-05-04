package by.tms.diplomrestc51.mapper;

import by.tms.diplomrestc51.entity.Device;
import by.tms.diplomrestc51.entity.device.WasherDevice;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface DeviceMapper {
    WasherDevice toWasherDevice(Device device);
}

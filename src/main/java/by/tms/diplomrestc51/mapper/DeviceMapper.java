package by.tms.diplomrestc51.mapper;

import by.tms.diplomrestc51.entity.Device;
import by.tms.diplomrestc51.entity.device.RefrigeratorDevice;
import by.tms.diplomrestc51.entity.device.WasherDevice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface DeviceMapper {
    WasherDevice deviceToWasherDevice(Device device);
    RefrigeratorDevice deviceToRefrigeratorDevice(Device device);
}

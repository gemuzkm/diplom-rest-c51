package by.tms.diplomrestc51.entity.device;

import by.tms.diplomrestc51.entity.Device;
import by.tms.diplomrestc51.entity.user.User;
import by.tms.diplomrestc51.enums.TypeDevice;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class WasherDevice extends Device {

    private TypeDevice typeDevice = TypeDevice.WASHER;
    private String model;
    private String serialNumber;
    private String firmwareVersion;
    private String ipAddress;
    private String macAddress;

    @Override
    public void setUser(User user) {
        super.setUser(user);
    }

    @Override
    public User getUser() {
        return super.getUser();
    }
}

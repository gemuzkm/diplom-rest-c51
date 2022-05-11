package by.tms.diplomrestc51.entity.device;

import by.tms.diplomrestc51.entity.Device;
import by.tms.diplomrestc51.entity.Parameter;
import by.tms.diplomrestc51.entity.user.User;
import by.tms.diplomrestc51.enums.TypeDevice;
import by.tms.diplomrestc51.enums.TypeParameter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class WasherDevice extends Device {

    @ManyToMany
    private List<Parameter> parameters;

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public TypeDevice getTypeDevice() {
        return super.getTypeDevice();
    }

    @Override
    public void setTypeDevice(TypeDevice typeDevice) {
        super.setTypeDevice(typeDevice);
    }

    @Override
    public String getModel() {
        return super.getModel();
    }

    @Override
    public void setModel(String model) {
        super.setModel(model);
    }

    @Override
    public String getSerialNumber() {
        return super.getSerialNumber();
    }

    @Override
    public void setSerialNumber(String serialNumber) {
        super.setSerialNumber(serialNumber);
    }

    @Override
    public String getFirmwareVersion() {
        return super.getFirmwareVersion();
    }

    @Override
    public void setFirmwareVersion(String firmwareVersion) {
        super.setFirmwareVersion(firmwareVersion);
    }

    @Override
    public String getIpAddress() {
        return super.getIpAddress();
    }

    @Override
    public void setIpAddress(String ipAddress) {
        super.setIpAddress(ipAddress);
    }

    @Override
    public String getMacAddress() {
        return super.getMacAddress();
    }

    @Override
    public void setMacAddress(String macAddress) {
        super.setMacAddress(macAddress);
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    public User getUser() {
        return super.getUser();
    }

    @Override
    public void setUser(User user) {
        super.setUser(user);
    }
}

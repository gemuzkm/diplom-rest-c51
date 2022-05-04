package by.tms.diplomrestc51.entity;

import by.tms.diplomrestc51.entity.user.User;
import by.tms.diplomrestc51.enums.TypeDevice;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "devices")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device extends BaseEntity {
    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeDevice typeDevice;

    @NotNull
    private String model;

    @Column(unique=true)
    private String serialNumber;
    private String firmwareVersion;
    private String ipAddress;

    @Column(unique=true)
    private String macAddress;
    private String description;
    private int temperature;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public TypeDevice getTypeDevice() {
        return typeDevice;
    }

    public void setTypeDevice(TypeDevice typeDevice) {
        this.typeDevice = typeDevice;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

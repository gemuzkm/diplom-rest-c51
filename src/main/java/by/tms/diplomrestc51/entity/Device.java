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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

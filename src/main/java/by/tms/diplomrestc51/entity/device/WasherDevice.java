package by.tms.diplomrestc51.entity.device;

import by.tms.diplomrestc51.entity.Device;
import by.tms.diplomrestc51.entity.user.User;
import by.tms.diplomrestc51.enums.Status;
import by.tms.diplomrestc51.enums.TypeDevice;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class WasherDevice extends Device {

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public Status getStatus() {
        return super.getStatus();
    }

    @Override
    public void setUser(User user) {
        super.setUser(user);
    }

    @Override
    public User getUser() {
        return super.getUser();
    }
}

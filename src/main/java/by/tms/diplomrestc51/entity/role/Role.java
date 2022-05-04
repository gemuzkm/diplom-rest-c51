package by.tms.diplomrestc51.entity.role;

import by.tms.diplomrestc51.entity.BaseEntity;
import by.tms.diplomrestc51.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public String toString() {
        return "Role{" +
                "id: " + super.getId() + ", " +
                "name: " + name + "}";
    }
}

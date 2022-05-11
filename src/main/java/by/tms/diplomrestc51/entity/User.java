package by.tms.diplomrestc51.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {
    @Size(min = 3, max = 25)
    private String username;

    private String password;

    @Size(min = 2, max = 25)
    private String firstName;

    @Size(min = 2, max = 25)
    private String lastName;

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Pattern(regexp = "^(\\+)?(\\(\\d{2,3}\\) ?\\d|\\d)(([ \\-]?\\d)|( ?\\(\\d{2,3}\\) ?)){5,12}\\d$")
    private String phone;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Role> roles;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Device> devices;

    @Override
    public String toString() {
        return "User{" +
                "id=" + super.getId() +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", roles=" + roles +
                ", status=" + super.getStatus() +
                '}';
    }
}

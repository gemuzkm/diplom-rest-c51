package by.tms.diplomrestc51.entity;

import by.tms.diplomrestc51.enums.TypeParameter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    private TypeParameter type;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parameter")
    private List<ParameterValues> values;

    public TypeParameter getType() {
        return type;
    }

    public void setType(TypeParameter type) {
        this.type = type;
    }

    public List<ParameterValues> getValues() {
        return values;
    }

    public void setValues(List<ParameterValues> values) {
        this.values = values;
    }
}

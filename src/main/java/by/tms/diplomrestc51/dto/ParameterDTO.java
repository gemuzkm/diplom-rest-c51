package by.tms.diplomrestc51.dto;

import by.tms.diplomrestc51.enums.TypeParameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterDTO {
    @NotNull
    private TypeParameter typeParameter;

    @NotNull
    private Double value;
}

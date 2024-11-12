package TIC.ServiceRequest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import TIC.ServiceRequest.model.Institute;

@Data
@NoArgsConstructor
public class DirectorDTO {

    @JsonIgnore
    private Long id;

    @NotNull
    @NotBlank
    private String cuit;

    @NotNull
    private Institute institute;

    private String name;
    private String lastname;
    private String phone;
    private String mail;

    @JsonIgnore
    private Boolean enabled;

}

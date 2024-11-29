package TIC.ServiceRequest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
public class DirectorDTO {

    @JsonIgnore
    private Long id;

    @NotNull
    @NotBlank
    private String cuit;

    @NotNull
    private String institute;

    private String name;
    private String lastname;
    private String phone;
    private String mail;

    @JsonIgnore
    private Boolean enabled;

}

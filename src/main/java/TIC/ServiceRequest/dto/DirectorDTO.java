package TIC.ServiceRequest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import TIC.ServiceRequest.model.Institute;

@Data
@NoArgsConstructor
public class DirectorDTO {

    @JsonIgnore
    Long id;

    private String cuit;
    private Institute institute;
    private String name;
    private String lastname;
    private String phone;
    private String mail;

    @JsonIgnore
    private Boolean enabled;

}

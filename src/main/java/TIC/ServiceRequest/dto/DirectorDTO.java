package TIC.ServiceRequest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import TIC.ServiceRequest.model.Institute;

@Data
@NoArgsConstructor
public class DirectorDTO {

    Long id;
    private String cuit;
    private Institute institute;
    private String name;
    private String lastname;
    private String phone;
    private String mail;
    private Boolean enabled;

}
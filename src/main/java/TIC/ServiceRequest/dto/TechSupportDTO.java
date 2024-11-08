package TIC.ServiceRequest.dto;

import TIC.ServiceRequest.model.Institute;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechSupportDTO {
    @JsonIgnore
    long id;
    private String code;
    private Institute institute;
    private String date;
    private String type;

}

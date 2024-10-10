package TIC.ServiceRequest.dto;

import TIC.ServiceRequest.model.Institute;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.GregorianCalendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechResponse {
    @JsonIgnore
    long id;
    private String code;
    private String state;
    private Institute institute;
    private String date;
    private String type;
}

package TIC.ServiceRequest.dto;

import TIC.ServiceRequest.model.SupportType;
import TIC.ServiceRequest.model.Institute;
import TIC.ServiceRequest.model.State;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.GregorianCalendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechRequest {
    @JsonIgnore
    long id;
    private String code;
    private State state;
    private Institute institute;
    private GregorianCalendar date;
    private SupportType type;
}

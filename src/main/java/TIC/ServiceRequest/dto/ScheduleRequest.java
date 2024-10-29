package TIC.ServiceRequest.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.GregorianCalendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {
    @JsonIgnore
    long id;
    private String code;
    private GregorianCalendar date;
    private String cuit;
}

package TIC.ServiceRequest.dto;




import TIC.ServiceRequest.model.Institute;
import TIC.ServiceRequest.model.State;

import TIC.ServiceRequest.model.SupportType;
import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;





import java.util.GregorianCalendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechRequest {
    @JsonIgnore
    long id;

    private String code;
    private Institute institute;
   private String type;

}

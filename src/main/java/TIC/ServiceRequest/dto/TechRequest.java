package TIC.ServiceRequest.dto;

import TIC.ServiceRequest.model.Institute;
import TIC.ServiceRequest.model.State;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.GregorianCalendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechRequest {
    @JsonIgnore
    long id;
    @NotNull
    private String code;
    private State state;
    @NotNull
    private Institute institute;
    private GregorianCalendar date;

}

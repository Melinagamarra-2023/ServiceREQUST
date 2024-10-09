package TIC.ServiceRequest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.GregorianCalendar;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tech_support")
public class TechSupport {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    long id;

    @Column (unique = true)
    private String code;
    private State state;
    @ManyToOne
    private Institute institute;
    private GregorianCalendar date;
    private SupportType type;



}

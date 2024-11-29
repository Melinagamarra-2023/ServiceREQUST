package TIC.ServiceRequest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.GregorianCalendar;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    private SupportType type;
    private GregorianCalendar date;
    @OneToMany
    (mappedBy = "techSupport")
    @JsonManagedReference
    private List<Log> logs;
    
}

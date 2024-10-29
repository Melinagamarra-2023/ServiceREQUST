package TIC.ServiceRequest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.GregorianCalendar;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private GregorianCalendar date;
    @Enumerated(EnumType.STRING)
    private State state;
    @ManyToOne
    @JoinColumn(name = "tech_support_id")
    private TechSupport techSupport;
}

package TIC.ServiceRequest.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "technicians")
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String cuit;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    private String name;
    private String lastname;
    private String phone;
    private String mail;
    private Boolean enabled;

}

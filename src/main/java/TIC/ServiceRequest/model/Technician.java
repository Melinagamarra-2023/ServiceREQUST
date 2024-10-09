package TIC.ServiceRequest.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="technicians")
public class Technician {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String cuit;

    @ManyToOne
    private Institute institute;

    String name;
    String lastname;
    String phone;
    String mail;
    Boolean enabled;

}

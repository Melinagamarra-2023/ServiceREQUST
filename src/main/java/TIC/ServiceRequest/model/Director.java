package TIC.ServiceRequest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="directors")
public class Director {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String cuit;

    @ManyToOne
    private Institute institute;

    private String name;
    private String lastname;
    private String phone;
    private String mail;
    private Boolean enabled;
}

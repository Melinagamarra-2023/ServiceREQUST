package TIC.ServiceRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import TIC.ServiceRequest.model.Technician;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long>{

}

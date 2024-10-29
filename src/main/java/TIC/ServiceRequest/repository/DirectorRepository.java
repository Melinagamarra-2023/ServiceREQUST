package TIC.ServiceRequest.repository;

import TIC.ServiceRequest.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long>{

}
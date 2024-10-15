package TIC.ServiceRequest.repository;

import TIC.ServiceRequest.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log,Long> {

}

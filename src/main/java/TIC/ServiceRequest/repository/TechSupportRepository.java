package TIC.ServiceRequest.repository;

import TIC.ServiceRequest.model.TechSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechSupportRepository extends JpaRepository<TechSupport,Long> {

    TechSupport findByCode(String code);
}

package TIC.ServiceRequest.service.Impl;

import TIC.ServiceRequest.dto.RequestTech;
import TIC.ServiceRequest.model.TechSupport;
import TIC.ServiceRequest.repository.TechSupportRepository;
import TIC.ServiceRequest.repository.TechnicianRepository;
import TIC.ServiceRequest.service.TechSupportService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.GregorianCalendar;

@Service
@Slf4j
public class TechSupportServiceImpl implements TechSupportService {
    private final TechSupportRepository repository;
    protected static final Logger logger = LoggerFactory.getLogger(TechSupportServiceImpl.class);

    public TechSupportServiceImpl(TechSupportRepository repository) {
        this.repository = repository;
    }

    @Override
    public void requestService(RequestTech requestTech) {
        TechSupport techSupport = toEntity(requestTech);
        try {
            repository.save(techSupport);
            logger.info("El servicio fue solicitado con éxito: {}", techSupport.getCode());
        } catch (Exception e) {
            logger.error("No se ha podido solicitar el servicio. Error: {}", e.getMessage());
        }
    }

    @Override
    public void scheduleService(GregorianCalendar date, Long id) {
       try {
           TechSupport techSupport = repository.findById(id).orElseThrow();
           techSupport.setDate(date);
           repository.save(techSupport);
           logger.info("El servicio fue agendado con exito: {} ", techSupport.getCode());
       } catch (Exception e) {
           logger.error("No se pudo agendar el servicio. Error: {}", e.getMessage());
       }

    }

    @Override
    public void acceptTechnician(String code) {
        

    }

    @Override
    public void acceptDirector(String code) {

    }


    private TechSupport toEntity(RequestTech requestTech) {
        TechSupport newTech = new TechSupport();
        newTech.setCode(requestTech.getCode());
        newTech.setDate(requestTech.getDate());
        newTech.setType(requestTech.getType());
        newTech.setInstitute(requestTech.getInstitute());
        newTech.setState(requestTech.getState());
        return newTech;
    }
}

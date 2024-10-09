package TIC.ServiceRequest.service.Impl;

import TIC.ServiceRequest.dto.RequestTech;
import TIC.ServiceRequest.model.State;
import TIC.ServiceRequest.model.TechSupport;
import TIC.ServiceRequest.repository.TechSupportRepository;
import TIC.ServiceRequest.service.TechSupportService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.GregorianCalendar;

import static TIC.ServiceRequest.constant.Constant.*;

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
            logger.info(SUCCESSFULLY_MESSAGE +"{}" , techSupport.getCode());
        } catch (Exception e) {
            logger.error(ERROR_MESAAGE+"{}",e.getMessage());

        }
    }

    @Override
    public void scheduleService(GregorianCalendar date, Long id) {
       try {
           TechSupport techSupport = repository.findById(id).orElseThrow();
           techSupport.setDate(date);
           repository.save(techSupport);
           logger.info(SUCCESSFULLY_MESSAGE_SCHEDULE +"{} ", techSupport.getCode());
       } catch (Exception e) {
           logger.error(ERROR_MESAAGE_SCHEDULE+" {}", e.getMessage());
       }

    }

    @Override
    public void acceptTechnician(String code) {
    try {
        TechSupport techSupport = repository.findByCode(code);
        techSupport.setState(State.PRESENTE);
        repository.save(techSupport);
        logger.info(PRESENCE_CONFIRMATION_MESSAGE +"{}",techSupport.getState());
    } catch (Exception e) {
       logger.error(PRESENCE_ERROR_MESSAGE);
    }

    }

    @Override
    public void acceptDirector(String code) {
        try {
           TechSupport techSupport = repository.findByCode(code);
           techSupport.setState(State.TERMINADO);
           repository.save(techSupport);
           logger.info(JOB_CONFIRMATION_MESSAGE+"{}", techSupport.getState());
        } catch (Exception e) {
            logger.info(JOB_ERROR_MESSAGE);
        }
    }

    @Override
    public void cenceledService(String code) {
        try {
            TechSupport techSupport = repository.findByCode(code);
            techSupport.setState(State.CANCELADO);
            repository.save(techSupport);
            logger.info(JOB_CANCELED+ "{}",techSupport.getState());
        } catch (Exception e) {
            logger.error(JOB_ERROR_CANCELED);
        }
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

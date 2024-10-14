package TIC.ServiceRequest.service.impl;

import TIC.ServiceRequest.dto.TechRequest;
import TIC.ServiceRequest.dto.TechResponse;
import TIC.ServiceRequest.model.*;
import TIC.ServiceRequest.repository.InstituteRepository;
import TIC.ServiceRequest.repository.TechSupportRepository;
import TIC.ServiceRequest.service.TechSupportService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.GregorianCalendar;

import static TIC.ServiceRequest.constant.Constant.*;

@Service
@Slf4j
public class TechSupportServiceImpl implements TechSupportService {
    private final TechSupportRepository repository;
    private final InstituteRepository instituteRepository;
    protected static final Logger logger = LoggerFactory.getLogger(TechSupportServiceImpl.class);

    public TechSupportServiceImpl(TechSupportRepository repository, InstituteRepository instituteRepository) {
        this.repository = repository;
        this.instituteRepository = instituteRepository;
    }

    @Override
    @Transactional
    public TechResponse requestService(TechRequest requestTech) {
        TechResponse response = new TechResponse();
        TechSupport techSupport = toEntity(requestTech);
        Institute institute = instituteRepository.findByCuise(requestTech.getInstitute().getCuise());
        if (institute == null) {
            logger.error("No hay ningun instituto cerado asociado a CUISE ingresado: {}", requestTech.getInstitute().getCuise());
            return response;
        }

        SupportType supportType = null;
        for (SupportType sp : SupportType.values()) {
            if (sp.name().equals(requestTech.getType())) {
                supportType = sp;

            }
        }
        if (supportType == null) {
            logger.error("El servicio solicitado, no se ha encontrado: {}", requestTech.getType());
            return response;
        }

        try {
            techSupport.setInstitute(institute);
            techSupport.setType(supportType);
            techSupport.setState(State.SOLICITADO);
            response = toDTO(repository.save(techSupport));
            logger.info(SUCCESSFULLY_MESSAGE +"{}" , techSupport.getCode());
        } catch (Exception e) {
            logger.error(ERROR_MESSAGE + "{}", e.getMessage());
        }
        return response;
    }

    @Override
    public void scheduleService(GregorianCalendar date, Long id) {
       try {
           TechSupport techSupport = repository.findById(id).orElseThrow();
           techSupport.setDate(date);
           techSupport.setState(State.AGENDADO);
           repository.save(techSupport);
           logger.info(SUCCESSFULLY_MESSAGE_SCHEDULE +"{} ", techSupport.getCode());
       } catch (Exception e) {
           logger.error(ERROR_MESSAGE_SCHEDULE+" {}", e.getMessage());
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


    private TechSupport toEntity(TechRequest techRequest) {
       TechSupport techSupport = new TechSupport();
      techSupport.setCode(techRequest.getCode());
      techSupport.setInstitute(techRequest.getInstitute());

        return techSupport;
    }

    private TechResponse toDTO(TechSupport techSupport) {
        TechResponse newTech = new TechResponse();
        newTech.setCode(techSupport.getCode());
        newTech.setDate(techSupport.getDate() != null? techSupport.getDate().toString() : "Todavía no se ha agendado");
        newTech.setInstitute(techSupport.getInstitute());
        newTech.setState(techSupport.getState().name());
        newTech.setType(techSupport.getType().name());
        return newTech;
    }
}

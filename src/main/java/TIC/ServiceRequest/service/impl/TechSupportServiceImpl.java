package TIC.ServiceRequest.service.impl;

import TIC.ServiceRequest.dto.ScheduleRequest;
import TIC.ServiceRequest.dto.TechRequest;
import TIC.ServiceRequest.dto.TechResponse;
import TIC.ServiceRequest.model.*;
import TIC.ServiceRequest.repository.InstituteRepository;
import TIC.ServiceRequest.repository.LogRepository;
import TIC.ServiceRequest.repository.TechSupportRepository;
import TIC.ServiceRequest.service.TechSupportService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.GregorianCalendar;

import static TIC.ServiceRequest.constant.TechSupportConstants.*;

@Service
@Slf4j
public class TechSupportServiceImpl implements TechSupportService {
    private final TechSupportRepository repository;
    private final LogRepository logRepository;
    private final InstituteRepository instituteRepository;
    protected static final Logger logger = LoggerFactory.getLogger(TechSupportServiceImpl.class);

    public TechSupportServiceImpl(TechSupportRepository repository, InstituteRepository instituteRepository, LogRepository logRepository) {
        this.logRepository = logRepository;
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
            response = toDTO(repository.save(techSupport));
            newLog(techSupport, State.SOLICITADO);
            logger.info(SUCCESSFULLY_MESSAGE + "{}", techSupport.getCode());
        } catch (Exception e) {
            logger.error(ERROR_MESSAGE + "{}", e.getMessage());
        }
        return response;


    }


    @Override
    public TechResponse scheduleService(GregorianCalendar date, ScheduleRequest request) {
        TechResponse response = new TechResponse();
        try {

            TechSupport techSupport = findByCode(request.getCode());
            techSupport.setDate(date);
            response = toDTO(repository.save(techSupport));
            newLog(techSupport, State.AGENDADO);
            logger.info(SUCCESSFULLY_MESSAGE_SCHEDULE + "{} ", techSupport.getCode());
        } catch (Exception e) {
            logger.error(ERROR_MESSAGE_SCHEDULE + " {}", e.getMessage());
        }

        return response;
    }



    @Override
    public TechResponse acceptTechnician(String code) {
        try {
            TechSupport techSupport = findByCode(code);
            TechResponse response = toDTO(repository.save(techSupport));
            newLog(techSupport, State.PRESENTE);
            logger.info(PRESENCE_CONFIRMATION_MESSAGE + "{}", techSupport.getCode());
            return response;
        } catch (Exception e) {
            logger.error(PRESENCE_ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public TechResponse acceptDirector(String code) {
        try {
            TechSupport techSupport = findByCode(code);
            TechResponse response = toDTO(repository.save(techSupport));
            newLog(techSupport, State.TERMINADO);
            logger.info(JOB_CONFIRMATION_MESSAGE + "{}", techSupport.getCode());
            return response;
        } catch (Exception e) {
            logger.info(JOB_ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public TechResponse cenceledService(String code) {
        try {
            TechSupport techSupport = findByCode(code);
            TechResponse response = toDTO(repository.save(techSupport));
            newLog(techSupport, State.CANCELADO);
            logger.info(JOB_CANCELED + "{}", techSupport.getCode());
            return response;
        } catch (Exception e) {
            logger.error(JOB_ERROR_CANCELED);
            return null;
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
        newTech.setInstitute(techSupport.getInstitute());
        newTech.setType(techSupport.getType().name());
        if(techSupport.getDate() != null){
            newTech.setDate(techSupport.getDate().getTime().toString());
        }

        return newTech;
    }

    private void newLog(TechSupport techSupport, State state) {
        Log newLog = new Log();
        newLog.setTechSupport(techSupport);
        newLog.setDate(new GregorianCalendar());
        newLog.setState(state);
        logRepository.save(newLog);

    }



    public TechSupport findByCode(String code){
        return repository.findAll().stream()
                .filter(techSupport -> techSupport.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

}

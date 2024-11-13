package TIC.ServiceRequest.service.impl;

import TIC.ServiceRequest.dto.TechSupportDTO;
import TIC.ServiceRequest.model.*;
import TIC.ServiceRequest.repository.InstituteRepository;
import TIC.ServiceRequest.repository.LogRepository;
import TIC.ServiceRequest.repository.TechSupportRepository;
import TIC.ServiceRequest.repository.TechnicianRepository;
import TIC.ServiceRequest.service.TechSupportService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static TIC.ServiceRequest.constant.TechSupportConstants.*;

@Service
@Slf4j
public class TechSupportServiceImpl implements TechSupportService {
    private final TechSupportRepository repository;
    private final LogRepository logRepository;
    private final InstituteRepository instituteRepository;
    private final List<Technician>technicians;
    private final TechnicianRepository technicianRepository;
    protected static final Logger logger = LoggerFactory.getLogger(TechSupportServiceImpl.class);

    public TechSupportServiceImpl(TechSupportRepository repository, InstituteRepository instituteRepository, LogRepository logRepository, TechnicianRepository technicianRepository) {
        this.logRepository = logRepository;
        this.repository = repository;
        this.instituteRepository = instituteRepository;
        this.technicianRepository = technicianRepository;
        technicians = new ArrayList<>();

    }

    @Override
    @Transactional
    public TechSupportDTO requestService(TechSupportDTO requestTech) {
        TechSupportDTO response = new TechSupportDTO();
        TechSupport techSupport = toEntity(requestTech);
        Institute institute = findByCuise(requestTech.getInstitute().getCuise());
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
    @Transactional
    public TechSupportDTO scheduleService(GregorianCalendar date, TechSupportDTO request) {
        TechSupportDTO response = new TechSupportDTO();
        try {
            TechSupport techSupport = findByCode(request.getCode());
            boolean technicianFound = false;
            for (Technician tec : technicianRepository.findAll()) {
                if (techSupport.getInstitute().getCuise().equals(tec.getInstitute().getCuise())) {
                    technicianFound = true;
                    break;
                }
            }
            if (!technicianFound) {
                logger.info(TECHNICIAN_ERROR);
                 }
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
    public TechSupportDTO acceptTechnician(Long id) {

            Optional<TechSupport>techSupportOptional = repository.findById(id);
            TechSupport techSupport = techSupportOptional.orElseThrow(()->{
                String message = PRESENCE_ERROR_MESSAGE + id;
                return new EntityNotFoundException(message);
            });
            TechSupportDTO response = toDTO(repository.save(techSupport));
            newLog(techSupport, State.PRESENTE);
            logger.info(PRESENCE_CONFIRMATION_MESSAGE + "{}", techSupport.getCode());
            return response;
    }

    @Override
    public TechSupportDTO acceptDirector(Long id) {

        Optional<TechSupport>techSupportOptional = repository.findById(id);
        TechSupport techSupport = techSupportOptional.orElseThrow(()->{
            String message = JOB_ERROR_MESSAGE + id;
            return new EntityNotFoundException(message);
        });
            TechSupportDTO response = toDTO(repository.save(techSupport));
            newLog(techSupport, State.TERMINADO);
            logger.info(JOB_CONFIRMATION_MESSAGE + "{}", techSupport.getCode());
            return response;

    }

    @Override
    public TechSupportDTO cenceledService(Long id) {
        Optional<TechSupport>techSupportOptional = repository.findById(id);
        TechSupport techSupport = techSupportOptional.orElseThrow(()->{
            String message = JOB_ERROR_CANCELED + id;
            return new EntityNotFoundException(message);
        });
            newLog(techSupport, State.CANCELADO);
            TechSupportDTO response = toDTO(repository.save(techSupport));
            logger.info(JOB_CANCELED + "{}", techSupport.getCode());
            return response;

    }


    private TechSupport toEntity(TechSupportDTO techRequest) {
        TechSupport techSupport = new TechSupport();
        techSupport.setCode(techRequest.getCode());
        techSupport.setInstitute(techRequest.getInstitute());

        return techSupport;
    }

    private TechSupportDTO toDTO(TechSupport techSupport) {
        TechSupportDTO newTech = new TechSupportDTO();
        newTech.setCode(techSupport.getCode());
        newTech.setInstitute(techSupport.getInstitute());
        newTech.setType(techSupport.getType().name());
        if(techSupport.getDate() != null){
            newTech.setDate(techSupport.getDate());
        }

        return newTech;
    }

    private void newLog(TechSupport techSupport, State state) {
        if (techSupport.getLogs() == null) {
            techSupport.setLogs(new ArrayList<>());
        }
        Log newLog = new Log();
        newLog.setTechSupport(techSupport);
        newLog.setDate(new GregorianCalendar());
        newLog.setState(state);
        logRepository.save(newLog);
        techSupport.getLogs().add(newLog);

    }



    private TechSupport findByCode(String code){
        return repository.findAll().stream()
                .filter(techSupport -> techSupport.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    private Institute findByCuise ( String cuise) {
        return instituteRepository.findAll().stream()
                .filter(institute -> institute.getCuise().equals(cuise))
                .findFirst()
                .orElse(null);
    }


}

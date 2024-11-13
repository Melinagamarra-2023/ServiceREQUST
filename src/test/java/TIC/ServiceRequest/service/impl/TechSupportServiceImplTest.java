package TIC.ServiceRequest.service.impl;

import TIC.ServiceRequest.dto.TechSupportDTO;
import TIC.ServiceRequest.model.*;
import TIC.ServiceRequest.repository.InstituteRepository;
import TIC.ServiceRequest.repository.LogRepository;
import TIC.ServiceRequest.repository.TechSupportRepository;
import TIC.ServiceRequest.repository.TechnicianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TechSupportServiceImplTest {
    @Autowired
    private TechSupportServiceImpl service;

    @MockBean
    private TechSupportRepository repository;
    @MockBean
    private LogRepository logRepository;
    @MockBean
    private InstituteRepository instituteRepository;
    @MockBean
    private TechnicianRepository technicianRepository;

    private List<Institute> instituteList;
    private List<TechSupportDTO>techSupports;
    private List<Technician>technicianList;
    private List<TechSupport>techSupportList;
    private List<Log>logs;


    @BeforeEach
    void setUp() {
        instituteList = new ArrayList<>();
        technicianList =new ArrayList<>();
        getInstitutes();
        techSupports = new ArrayList<>();
        techSupportList = new ArrayList<>();
        logs = new ArrayList<>();
        createTechSupport();

    }

    @Test
    void requestService() {
        TechSupportDTO expected = techSupports.get(0);
        when(repository.save(any(TechSupport.class))).thenReturn(techSupportList.get(0));
        when(instituteRepository.findAll()).thenReturn(instituteList);
        TechSupportDTO result = service.requestService(expected);
        assertEquals(expected.getCode(),result.getCode());
    }

    @Test
    void scheduleService() {
        TechSupportDTO expected = techSupports.get(0);
        when(repository.save(any(TechSupport.class))).thenReturn(techSupportList.get(0));
        when(instituteRepository.findAll()).thenReturn(instituteList);
        when(repository.findAll()).thenReturn(techSupportList);
        TechSupportDTO result = service.scheduleService(new GregorianCalendar(),expected);
        assertNotNull(result);
        assertEquals("4321",result.getCode());
    }

    @Test
    void acceptTechnician() {
        TechSupportDTO expected =techSupports.get(0);
        TechSupport techSupport = techSupportList.get(0);
        when(repository.findById(techSupport.getId())).thenReturn(Optional.of(techSupport));
        when(repository.save(any(TechSupport.class))).thenReturn(techSupport);
        TechSupportDTO result = service.acceptTechnician(expected.getId());
        assertNotNull(result);
        assertEquals(expected.getCode(),result.getCode());
        verify(repository).findById(techSupport.getId());
        verify(repository).save(techSupport);
    }

    @Test
    void acceptDirector() {
        TechSupportDTO expected =techSupports.get(0);
        TechSupport techSupport = techSupportList.get(0);
        when(repository.findById(techSupport.getId())).thenReturn(Optional.of(techSupport));
        when(repository.save(any(TechSupport.class))).thenReturn(techSupport);
        TechSupportDTO result = service.acceptDirector(expected.getId());
        assertNotNull(result);
        assertEquals(expected.getCode(),result.getCode());
        verify(repository).findById(techSupport.getId());
        verify(repository).save(techSupport);
    }

    @Test
    void cenceledService() {
        TechSupportDTO expected =techSupports.get(0);
        TechSupport techSupport = techSupportList.get(0);
        when(repository.findById(techSupport.getId())).thenReturn(Optional.of(techSupport));
        when(repository.save(any(TechSupport.class))).thenReturn(techSupport);
        TechSupportDTO result = service.cenceledService(expected.getId());
        assertNotNull(result);
        assertEquals(expected.getCode(),result.getCode());
        verify(repository).findById(techSupport.getId());
        verify(repository).save(techSupport);
    }

    private void getInstitutes(){
        Institute institute = new Institute();
        institute.setId(1L);
        institute.setCuise("4321");
        institute.setTechnicians(technicianList);
        instituteList.add(institute);
        Technician technician = new Technician();
        technician.setId(1L);
        technician.setCuit("111111111");
        technician.setInstitute(instituteList.get(0));
        technicianList.add(technician);
    }

    private void createTechSupport(){
        TechSupportDTO techSupportDTO = new TechSupportDTO();
        techSupportDTO.setCode("4321");
        techSupportDTO.setId(1L);
        techSupportDTO.setInstitute(instituteList.get(0));
        techSupportDTO.setType(SupportType.ARREGLOS.name());
        techSupportDTO.setCuit("111111111");
        techSupports.add(techSupportDTO);

        TechSupport techSupport = new TechSupport();
        techSupport.setId(1L);
        techSupport.setCode("4321");
        techSupport.setInstitute(instituteList.get(0));
        techSupport.setType(SupportType.ARREGLOS);
        techSupport.setLogs(logs);
        techSupportList.add(techSupport);


    }
}
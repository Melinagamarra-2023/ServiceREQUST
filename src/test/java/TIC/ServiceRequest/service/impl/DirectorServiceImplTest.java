package TIC.ServiceRequest.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import TIC.ServiceRequest.model.Director;
import TIC.ServiceRequest.dto.DirectorDTO;
import TIC.ServiceRequest.model.Institute;
import TIC.ServiceRequest.repository.DirectorRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DirectorServiceImplTest {

    List<Director> directors = new ArrayList<>();
    List<DirectorDTO> directorsDTO = new ArrayList<>();

    @MockBean
    DirectorRepository repository;

    @Autowired
    DirectorServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(DirectorRepository.class);
        service = new DirectorServiceImpl(repository);
        repositoryMock();
        createDirectors();
    }

    @Test
    void createSuccessful() {
        when(repository.save(any())).thenReturn(
                new Director(1L, "123455", null, "", "", "", "", true));
        assertEquals("123455", service.create(directorsDTO.get(1)).getCuit());
        verify(repository, times(1)).save(any());
    }

    @Test
    void createWrong() {
        directorsDTO.get(0).setCuit("");
        assertNull(service.create(directorsDTO.get(0)));
        verify(repository, never()).save(any(Director.class));
    }

    @Test
    void createRepeated() {
        assertNull(service.create(directorsDTO.get(0)));
        verify(repository, never()).save(any(Director.class));
    }

    @Test
    void readOneExist() {
        String expected = "123454";
        when(repository.findById(4L)).thenReturn(Optional.ofNullable(directors.get(3)));
        assertEquals(expected, service.readOne(4L).getCuit());
    }

    @Test
    void readOneNonExist() {
        when(repository.findById(4L)).thenReturn(Optional.ofNullable(directors.get(3)));
        assertNull(service.readOne(7L));
    }

    @Test
    void readByCuitExist() {
        String expected = "123453";
        assertEquals(expected, service.readByCuit(expected).getCuit());
        verify(repository, times(1)).findAll();
    }

    @Test
    void readByCuitNonExist() {
        assertNull(service.readByCuit("111"));
        verify(repository, times(1)).findAll();
    }

    @Test
    void readByInstituteExist() {
        int expected = 2;
        assertEquals(expected, service.readByInstitute("212345").size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void readByInstituteNonExist() {
        int expected = 0;
        assertEquals(expected, service.readByInstitute("111").size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void readAll() {
        int expected = 4;
        assertEquals(expected, service.readAll().size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void updateSuccessful() {
        when(repository.save(any(Director.class))).thenReturn(directors.get(1));
        directors.get(1).setName("nuevo nombre");
        assertEquals("nuevo nombre", service.update("123452", directorsDTO.get(0)).getName());
    }

    @Test
    void updateWrong() {
        directorsDTO.get(1).setName("");
        assertNull(service.update("123452", directorsDTO.get(1)));
        verify(repository, never()).save(any(Director.class));
    }

    @Test
    void disableExist() {
        when(repository.save(any(Director.class))).thenReturn(directors.get(3));
        assertFalse(service.disable("123454").getEnabled());
        verify(repository, times(2)).save(any(Director.class));
    }

    @Test
    void disableNonExist() {
        assertNull(service.disable("123459"));
        verify(repository, never()).save(any(Director.class));
    }

    @Test
    void disableAlreadyDisabled() {
        directors.get(3).setEnabled(false);
        assertNull(service.disable("123454"));
        verify(repository, never()).save(any(Director.class));
    }

    @Test
    void enableExist() {
        when(repository.save(any(Director.class))).thenReturn(directors.get(2));
        assertTrue(service.enable("123453").getEnabled());
        verify(repository, times(2)).save(any(Director.class));
    }

    @Test
    void enableNonExist() {
        assertNull(service.enable("123457"));
        verify(repository, never()).save(any(Director.class));
    }

    @Test
    void enableAlreadyEnabled() {
        directors.get(2).setEnabled(true);
        assertNull(service.enable("123453"));
        verify(repository, never()).save(any(Director.class));
    }

    void createDirectors() {
        Director director1 = new Director();
        Director director2 = new Director();
        Director director3 = new Director();
        Director director4 = new Director();
        Institute institute1 = new Institute();
        Institute institute2 = new Institute();
        Institute institute3 = new Institute();
        DirectorDTO directorDTO1 = new DirectorDTO();
        DirectorDTO directorDTO2 = new DirectorDTO();
        //
        director1.setCuit("123451");
        director1.setName("1nombre");
        director1.setLastname("1apellido");
        director1.setMail("1@gmail.com");
        director1.setPhone("37641");
        //
        director2.setCuit("123452");
        director2.setName("2nombre");
        director2.setLastname("2apellido");
        director2.setMail("2@gmail.com");
        director2.setPhone("37642");
        //
        director3.setCuit("123453");
        director3.setName("3nombre");
        director3.setLastname("3apellido");
        director3.setMail("3@gmail.com");
        director3.setPhone("37643");
        director3.setEnabled(false);
        //
        director4.setCuit("123454");
        director4.setName("4nombre");
        director4.setLastname("4apellido");
        director4.setMail("4@gmail.com");
        director4.setPhone("37644");
        //
        directorDTO1.setCuit("123452");
        directorDTO1.setName("2nombre");
        directorDTO1.setLastname("2apellido");
        directorDTO1.setMail("2@gmail.com");
        directorDTO1.setPhone("37642");
        //
        directorDTO2.setCuit("123455");
        directorDTO2.setName("nombreNombre");
        directorDTO2.setLastname("apellidoApellido");
        directorDTO2.setMail("gmail@gmail.com");
        directorDTO2.setPhone("37643764");
        //
        institute1.setId(1L);
        institute1.setCuise("112345");
        institute2.setId(2L);
        institute2.setCuise("212345");
        institute3.setId(3L);
        institute3.setCuise("312345");
        director1.setInstitute(institute1);
        director2.setInstitute(institute2);
        director3.setInstitute(institute3);
        director4.setInstitute(institute2);
        directorDTO1.setInstitute(institute1);
        directorDTO2.setInstitute(institute3);
        //
        directors.add(director1);
        directors.add(director2);
        directors.add(director3);
        directors.add(director4);
        directorsDTO.add(directorDTO1);
        directorsDTO.add(directorDTO2);
    }

    void repositoryMock() {
        when(repository.findAll()).thenReturn(directors);
    }

}
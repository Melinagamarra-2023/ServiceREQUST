package TIC.ServiceRequest.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import TIC.ServiceRequest.model.Director;
import TIC.ServiceRequest.dto.DirectorDTO;
import TIC.ServiceRequest.model.Institute;
import TIC.ServiceRequest.repository.DirectorRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;


import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        createDirectors();
    }

    @Test
    void correctSave() {
        String expected = "123451";
        when(repository.save(any())).thenReturn(directors.get(0));
        assertEquals(expected, service.save(directorsDTO.get(0)).getCuit());
    }

    @Test
    void wrongSave() {
        directorsDTO.get(0).setCuit("");
        service.save(directorsDTO.get(0));
        verify(repository, never()).save(any(Director.class));
    }

    @Test
    void repeatedSave() {
        when(repository.findAll()).thenReturn(directors);
        service.save(directorsDTO.get(0));
        verify(repository, never()).save(any(Director.class));
    }

    @Test
    void readOne() {
        String expected = "123454";
        when(repository.findById(4L)).thenReturn(Optional.ofNullable(directors.get(3)));
        assertEquals(expected, service.readOne(4L).getCuit());
    }

    @Test
    void readByCuit() {
        String expected = "123453";
        when(repository.findAll()).thenReturn(directors);
        assertEquals(expected, service.readByCuit(expected).getCuit());
    }

    @Test
    void readByInstitute() {
        int expected = 2;
        when(repository.findAll()).thenReturn(directors);
        assertEquals(expected, service.readByInstitute("212345").size());
    }

    @Test
    void readAll() {
        int expected = 4;
        when(repository.findAll()).thenReturn(directors);
        assertEquals(expected, service.readAll().size());
    }

    @Test
    void update() {
        when(repository.findAll()).thenReturn(directors);
        service.update("123451", directorsDTO.get(0));
        verify(repository, times(1)).save(any(Director.class));
    }

    @Test
    void disableExist() {
        when(repository.findAll()).thenReturn(directors);
        service.enable("123451");
        verify(repository, times(1)).save(any(Director.class));
    }

    @Test
    void disableNonExist() {
        when(repository.findAll()).thenReturn(directors);
        service.enable("123459");
        verify(repository, never()).save(any(Director.class));
    }

    @Test
    void enableExist() {
        when(repository.findAll()).thenReturn(directors);
        service.enable("123453");
        verify(repository, times(1)).save(any(Director.class));
    }

    @Test
    void enableNonExist() {
        when(repository.findAll()).thenReturn(directors);
        service.enable("123457");
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
        DirectorDTO directorDTO = new DirectorDTO();
        director1.setCuit("123451");
        director1.setName("1nombre");
        director1.setLastname("1apellido");
        director1.setMail("1@gmail.com");
        director1.setPhone("37641");
        director2.setCuit("123452");
        director2.setName("2nombre");
        director2.setLastname("2apellido");
        director2.setMail("2@gmail.com");
        director2.setPhone("37642");
        director3.setCuit("123453");
        director3.setName("3nombre");
        director3.setLastname("3apellido");
        director3.setMail("3@gmail.com");
        director3.setPhone("37643");
        director4.setCuit("123454");
        director4.setName("4nombre");
        director4.setLastname("4apellido");
        director4.setMail("4@gmail.com");
        director4.setPhone("37644");
        directorDTO.setCuit("123452");
        directorDTO.setName("2nombre");
        directorDTO.setLastname("2apellido");
        directorDTO.setMail("2@gmail.com");
        directorDTO.setPhone("37642");
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
        directorDTO.setInstitute(institute1);
        directors.add(director1);
        directors.add(director2);
        directors.add(director3);
        directors.add(director4);
        directorsDTO.add(directorDTO);
    }



}
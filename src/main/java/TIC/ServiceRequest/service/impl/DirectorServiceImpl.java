package TIC.ServiceRequest.service.impl;


import TIC.ServiceRequest.model.Institute;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;
import TIC.ServiceRequest.model.Director;
import TIC.ServiceRequest.dto.DirectorDTO;
import org.springframework.stereotype.Service;
import TIC.ServiceRequest.service.DirectorService;
import org.springframework.dao.DataAccessException;
import TIC.ServiceRequest.repository.DirectorRepository;
import static TIC.ServiceRequest.constant.Constant.*;

import java.util.List;
import java.util.ArrayList;


@Service
@Slf4j
public class DirectorServiceImpl implements DirectorService {

    DirectorRepository repository;
    protected static final Logger logger = LoggerFactory.getLogger(DirectorServiceImpl.class);

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        repository = directorRepository;
    }


    public DirectorDTO save(DirectorDTO director) throws DataAccessException {
        try {
            Director newDirector = toEntity(director);
            logger.info("Guardando al directivo: {}", newDirector.getCuit());
            return toDTO(repository.save(newDirector));
        } catch (Exception e) {
            logger.error("No se ha podido guardar el directivo {}. Error: {}", director.getCuit(), e.getMessage());
        }
        return null;

    }

    @Override
    public DirectorDTO readOne(Long id) throws DataAccessException {
        try {

            logger.info("Buscando directivo bajo el ID: {}", id);
            return toDTO(repository.findById(id).orElseThrow());
        } catch (Exception e) {
            searchError(e);
        }
        return null;
    }

    public DirectorDTO readByCuit(String cuit) throws DataAccessException {
        try {
            logger.info("Buscando directivo bajo el CUIT: {}", cuit);
            return toDTO(repository.findAll().stream()
                    .filter(director -> director.getCuit().equals(cuit)).findFirst().orElseThrow());
        } catch (Exception e) {
            searchError(e);

        }
        return null;
    }

    @Override
    public List<DirectorDTO> readAll() throws DataAccessException {

        try {
            List<DirectorDTO> response = new ArrayList<>();
            logger.info("Buscando todos los directivos");
            for (Director director : repository.findAll()){
                response.add(toDTO(director));
            }
            return response;
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public DirectorDTO update(DirectorDTO director) {
        try {
            logger.info("Actualizando directivo bajo el CUIT: {}", director.getCuit());
            Director oldDirector = toEntity(readOne(director.getId()));
            Director newDirector = toEntity(director);
            oldDirector.setName(newDirector.getName());
            oldDirector.setLastname(newDirector.getLastname());
            oldDirector.setPhone(newDirector.getPhone());
            oldDirector.setMail(newDirector.getMail());
            return toDTO(repository.save(newDirector));
        } catch (Exception e) {
            logger.error("No se ha podido actulizar al directivo {}. Error: {}", director.getCuit(), e.getMessage());
        }
    }

    @Override
    public DirectorDTO delete(DirectorDTO director) {
        try {
            logger.info("Eliminando al directivo bajo el CUIT: {}", director.getCuit());
            Director delDirector = toEntity(director);
            delDirector.setEnabled(false);
            return toDTO(repository.save(delDirector));
        } catch (Exception e) {
            logger.error("No se ha podido eliminar al directivo {}. Error {}", director.getCuit(), e.getMessage());
        }

    }

    @Override
    public Director toEntity(DirectorDTO dto) {
        Director entity = new Director();
        entity.setId(dto.getId());
        entity.setCuit(dto.getCuit());
        entity.setInstitute(dto.getInstitute());
        entity.setName(dto.getName());
        entity.setLastname(dto.getLastname());
        entity.setPhone(dto.getPhone());
        entity.setMail(dto.getMail());
        entity.setEnabled(dto.getEnabled());
        return entity;
    }

    @Override
    public DirectorDTO toDTO(Director director) {
        DirectorDTO dto = new DirectorDTO();
        dto.setId(director.getId());
        dto.setCuit(director.getCuit());
        dto.setInstitute(director.getInstitute());
        dto.setName(director.getName());
        dto.setLastname(director.getLastname());
        dto.setPhone(director.getPhone());
        dto.setMail(director.getMail());
        dto.setEnabled(director.getEnabled());
        return dto;
    }


    private void searchError(Exception e){
        logger.error(ERROR_SEARCH+" {}", e.getMessage());
    }

}

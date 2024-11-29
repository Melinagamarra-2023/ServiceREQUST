package TIC.ServiceRequest.service.impl;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;
import TIC.ServiceRequest.model.Director;
import TIC.ServiceRequest.dto.DirectorDTO;
import TIC.ServiceRequest.model.Institute;
import org.springframework.stereotype.Service;
import TIC.ServiceRequest.service.DirectorService;
import org.springframework.dao.DataAccessException;
import TIC.ServiceRequest.repository.DirectorRepository;
import TIC.ServiceRequest.repository.InstituteRepository;

import static TIC.ServiceRequest.constant.DirectorConstants.*;

@Slf4j
@Service
public class DirectorServiceImpl implements DirectorService {

    DirectorRepository directorRepository;
    InstituteRepository instituteRepository;
    protected static final Logger logger = LoggerFactory.getLogger(DirectorServiceImpl.class);

    public DirectorServiceImpl(DirectorRepository directorRepository, InstituteRepository instituteRepository) {
        this.directorRepository = directorRepository;
        this.instituteRepository = instituteRepository;
    }

    @Override
    public DirectorDTO create(DirectorDTO director) throws DataAccessException {
        try {
            Director newDirector = toEntity(director);
            logger.info("Guardando al directivo: {}", newDirector.getCuit());
            newDirector.setEnabled(true);
            if (readByCuit(director.getCuit()) != null) {
                throw new Exception("Este cuit ya existe en el sistema");
            } else if (director.getCuit().isBlank()) {
                throw new Exception("No se permite cuit vacio");
            }
            return toDTO(directorRepository.save(newDirector));
        } catch (Exception e) {
            logger.error("No se ha podido guardar el directivo {}. Error: {}", director.getCuit(), e.getMessage());
        }
        return null;
    }

    @Override
    public DirectorDTO readOne(Long id) throws DataAccessException {
        try {
            logger.info("Buscando directivo bajo el ID: {}", id);
            return toDTO(directorRepository.findById(id).orElseThrow());
        } catch (Exception e) {
            searchError(e);
        }
        return null;
    }

    public DirectorDTO readByCuit(String cuit) throws DataAccessException {
        try {
            logger.info("Buscando directivo bajo el CUIT: {}", cuit);
            List<Director> directors = directorRepository.findAll();
            return toDTO(directors.stream()
                    .filter(director -> director.getCuit().equals(cuit)).findFirst().orElseThrow());
        } catch (Exception e) {
            searchError(e);
        }
        return null;
    }

    public List<DirectorDTO> readByInstitute(String instituteCuise) throws DataAccessException {
        try {
            List<DirectorDTO> response = new ArrayList<>();
            logger.info("Buscando todos los directivos pertenecientes a una Institución determinada");
            for (Director director : directorRepository.findAll()) {
                if (director.getInstitute().getCuise().equals(instituteCuise)) {
                    response.add(toDTO(director));
                }
            }
            return response;
        } catch (Exception e) {
            searchError(e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<DirectorDTO> readAll() throws DataAccessException {
        try {
            List<DirectorDTO> response = new ArrayList<>();
            logger.info("Buscando todos los directivos");
            for (Director director : directorRepository.findAll()) {
                response.add(toDTO(director));
            }
            return response;
        } catch (Exception e) {
            searchError(e);
        }
        return new ArrayList<>();
    }

    @Override
    public DirectorDTO update(String cuit, DirectorDTO director) {
        try {
            logger.info("Actualizando directivo bajo el CUIT: {}", director.getCuit());
            Director oldDirector = toEntity(readByCuit(cuit));
            Director newDirector = toEntity(director);
            if (newDirector.getName().isEmpty() || newDirector.getLastname().isEmpty() ||
                    newDirector.getPhone().isEmpty() || newDirector.getMail().isEmpty()) {
                throw new Exception("No se admiten datos vacios");
            }
            oldDirector.setName(newDirector.getName());
            oldDirector.setLastname(newDirector.getLastname());
            oldDirector.setPhone(newDirector.getPhone());
            oldDirector.setMail(newDirector.getMail());
            return toDTO(directorRepository.save(oldDirector));
        } catch (Exception e) {
            logger.error("No se ha podido actulizar al directivo {}. Error: {}", director.getCuit(), e.getMessage());
        }
        return null;
    }

    @Override
    public DirectorDTO disable(String cuit) {
        try {
            logger.info("Eliminando al directivo bajo el CUIT: {}", cuit);
            Director entity = toEntity(readByCuit(cuit));
            if (Boolean.FALSE.equals(entity.getEnabled())) {
                throw new Exception("Directivo ya deshabilitado.");
            }
            directorRepository.save(entity).setEnabled(false);
            return toDTO(directorRepository.save(entity));
        } catch (Exception e) {
            logger.error("No se ha podido eliminar al directivo {}. Error {}", cuit, e.getMessage());
        }
        return null;
    }

    @Override
    public DirectorDTO enable(String cuit) {
        try {
            logger.info("Habilitando al directivo bajo el CUIT: {}", cuit);
            Director entity = toEntity(readByCuit(cuit));
            if (Boolean.TRUE.equals(entity.getEnabled())) {
                throw new Exception("Directivo ya Habilitado.");
            }
            directorRepository.save(entity).setEnabled(true);
            return toDTO(directorRepository.save(entity));
        } catch (Exception e) {
            logger.error("No se ha podido habilitar al directivo {}. Error {}", cuit, e.getMessage());
        }
        return null;
    }

    private Institute verifyInstitute(String cuise) {
        for (Institute institute : instituteRepository.findAll()) {
            if (institute.getCuise().equals(cuise)) {
                return institute;
            }
        }
        return null;
    }

    @Override
    public Director toEntity(DirectorDTO dto) {
        Director entity = new Director();
        entity.setId(dto.getId());
        entity.setCuit(dto.getCuit());
        entity.setInstitute(verifyInstitute(dto.getInstitute()));
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
        dto.setInstitute(director.getInstitute().getCuise());
        dto.setName(director.getName());
        dto.setLastname(director.getLastname());
        dto.setPhone(director.getPhone());
        dto.setMail(director.getMail());
        dto.setEnabled(director.getEnabled());
        return dto;
    }

    private void searchError(Exception e) {
        logger.error(SERV_ERROR_SEARCH + " {}", e.getMessage());
    }

}

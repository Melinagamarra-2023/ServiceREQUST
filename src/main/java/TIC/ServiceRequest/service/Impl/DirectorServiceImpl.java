package TIC.ServiceRequest.service.Impl;

import TIC.ServiceRequest.dto.DirectorDTO;
import TIC.ServiceRequest.model.Director;
import TIC.ServiceRequest.service.DirectorService;
import TIC.ServiceRequest.repository.DirectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DirectorServiceImpl implements DirectorService {

    DirectorRepository repository;
    protected static final Logger logger = LoggerFactory.getLogger(DirectorServiceImpl.class);

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        repository = directorRepository;
    }

    @Override
    public String save(DirectorDTO directorDTO) throws DataAccessException {
        try {
            Director newDirector = toEntity(directorDTO);
            repository.save(newDirector);
            String info = "El director ha sido guardado con éxito: " + newDirector.getCuit();
            logger.info(info);
            return info;
        } catch (Exception e) {
            String error = "No se ha podido guardar el directivo. Error: " + e.getMessage();
            logger.error(error);
            return error;
        }
    }

    @Override
    public DirectorDTO readOne(Long id) throws DataAccessException {
        try {
            return toDTO(repository.findById(id).orElseThrow());
        } catch (Exception e) {
            logger.error("No se ha encontrado el directivo buscado. Error: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public List<DirectorDTO> readAll() throws DataAccessException {
        List<DirectorDTO> beReturned = new ArrayList<>();
        try {
            for (Director director : repository.findAll()){
                beReturned.add(toDTO(director));
            }
            return beReturned;
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
        return beReturned;
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

}

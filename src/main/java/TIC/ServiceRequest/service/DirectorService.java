package TIC.ServiceRequest.service;

import TIC.ServiceRequest.model.Director;
import TIC.ServiceRequest.dto.DirectorDTO;

public interface DirectorService extends ServiceCRUD<DirectorDTO, DirectorDTO>{
    Director toEntity(DirectorDTO dto);
    DirectorDTO toDTO(Director director);
}

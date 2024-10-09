package TIC.ServiceRequest.service;

import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ServiceCRUD<REQUEST, RESPONSE> {

    String save(REQUEST request) throws DataAccessException;

    RESPONSE readOne(Long id) throws DataAccessException;

    List<RESPONSE> readAll() throws DataAccessException;

}

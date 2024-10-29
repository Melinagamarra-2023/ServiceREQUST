package TIC.ServiceRequest.service;

import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ServiceCRUD<REQUEST, RESPONSE> {

    RESPONSE save(REQUEST request) throws DataAccessException;

    RESPONSE readOne(Long id) throws DataAccessException;

    List<RESPONSE> readAll() throws DataAccessException;

    RESPONSE update(REQUEST request) throws DataAccessException;

    RESPONSE disable(REQUEST request) throws DataAccessException;

    RESPONSE enable(REQUEST request) throws DataAccessException;

}

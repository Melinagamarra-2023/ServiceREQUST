package TIC.ServiceRequest.service;

import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ServiceCRUD<REQUEST, RESPONSE> {

    RESPONSE create(REQUEST request) throws DataAccessException;

    RESPONSE readOne(Long id) throws DataAccessException;

    List<RESPONSE> readAll() throws DataAccessException;

    RESPONSE update(String cuit, REQUEST request) throws DataAccessException;

    RESPONSE disable(String cuit) throws DataAccessException;

    RESPONSE enable(String cuit) throws DataAccessException;

}

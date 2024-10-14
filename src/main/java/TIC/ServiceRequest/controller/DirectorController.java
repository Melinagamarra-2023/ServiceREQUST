package TIC.ServiceRequest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;
import TIC.ServiceRequest.dto.DirectorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import TIC.ServiceRequest.service.impl.DirectorServiceImpl;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping(DirectorController.RESOURCE)
public class DirectorController {

    public static final String ID = "/{cuit}";
    public static final String RESOURCE = "/api/directors";
    private static final Logger logger = LoggerFactory.getLogger(DirectorController.class);
    private final DirectorServiceImpl service;

    public DirectorController(DirectorServiceImpl directorService) {
        this.service = directorService;
    }

    @PostMapping(value = "")
    public ResponseEntity<DirectorDTO> create(@RequestBody DirectorDTO request) throws URISyntaxException {
        logger.info("Creating new director : {}", request);
        DirectorDTO response;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "DirectorController");
        if (request.getId() != null) {
            headers.add("ERROR", "ID is not null");
            return ResponseEntity.badRequest().headers(headers).body(request);
        }
        response = this.service.save(request);
        URI uri = new URI("/api/directors/" + response.getId());
        headers.setLocation(uri);
        return ResponseEntity.ok().headers(headers).body(response);
    }

    @GetMapping(value = ID)
    public ResponseEntity<DirectorDTO> readOne(@PathVariable(value = "cuit") String cuit) {
        logger.info("Get director: {}", cuit);
        DirectorDTO response = service.readByCuit(cuit);
        if (response == null) {
            return ResponseEntity.badRequest().headers(notFound(cuit)).body(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<DirectorDTO>> readAll() {
        logger.info("Get all directors");
        List<DirectorDTO> response = new ArrayList<>(service.readAll());
        HttpHeaders headers = new HttpHeaders();
        if (response.isEmpty()) {
            headers.add("NOT_FOUND", "There are no directors in the database");
            return ResponseEntity.badRequest().headers(headers).body(null);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PutMapping(value = ID)
    public ResponseEntity<DirectorDTO> update(@PathVariable(value = "cuit") String directorCuit,
                                           @RequestBody DirectorDTO newDirector) {
        logger.info("Update director with cuit {}", directorCuit);
        DirectorDTO oldDirector = service.readByCuit(directorCuit);
        if (oldDirector == null) {
            return ResponseEntity.badRequest().headers(notFound(directorCuit)).body(null);
        }
        service.update(newDirector);
        return new ResponseEntity<>(service.readByCuit(directorCuit), HttpStatus.OK);
    }

    @PutMapping(value = ID)
    public ResponseEntity<Void> delete(@PathVariable(value = "cuit") String directorCuit) {
        logger.info("Delete director with cuit {}", directorCuit);
        DirectorDTO delDirector = service.readByCuit(directorCuit);
        if (delDirector == null) {
            return ResponseEntity.badRequest().headers(notFound(directorCuit)).body(null);
        }
        service.delete(delDirector);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private HttpHeaders notFound(String cuit) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("NOT_FOUND", "Director with cuit " + cuit + " does not exist");
        return headers;
    }

}

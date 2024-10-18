package TIC.ServiceRequest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;
import TIC.ServiceRequest.dto.DirectorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import TIC.ServiceRequest.service.impl.DirectorServiceImpl;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.net.URISyntaxException;

import static TIC.ServiceRequest.constant.DirectorConstants.*;

@Slf4j
@RestController
@RequestMapping(DirectorController.RESOURCE)
public class DirectorController {

    private final DirectorServiceImpl service;
    public static final String CUIT = "/{cuit}";
    public static final String RESOURCE = "/api/directors";
    private static final Logger logger = LoggerFactory.getLogger(DirectorController.class);

    public DirectorController(DirectorServiceImpl directorService) {
        this.service = directorService;
    }

    @PostMapping(value = "")
    public ResponseEntity<DirectorDTO> create(@RequestBody DirectorDTO request) throws URISyntaxException {
        logger.info("Creating new director: {}", request);
        DirectorDTO response;
        if (service.readByCuit(request.getCuit()) != null) {
            logger.info("Creating new director - error");
            return ResponseEntity.badRequest()
                    .headers(header("CREATE_ERROR", startDescription(request.getCuit())+" already exist"))
                    .body(request);
        }
        response = this.service.save(request);
        URI uri = new URI("/api/directors/" + response.getCuit());
        logger.info("Director created");
        return ResponseEntity.ok().headers(header(SUCCESFULL, "Director created")).location(uri).body(response);
    }

    @GetMapping(value = CUIT)
    public ResponseEntity<DirectorDTO> readOne(@PathVariable(value = "cuit") String cuit) {
        logger.info("Get director: {}", cuit);
        DirectorDTO response = service.readByCuit(cuit);
        if (response == null) {
            errorSearch();
            return ResponseEntity.badRequest().headers(header(NOT_FOUND, notFoundDescription(cuit))).body(null);
        }
        correctlySearch();
        return ResponseEntity.ok().headers(header(SUCCESFULL, FOUND)).body(response);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<DirectorDTO>> readByInstitute(String instituteCuise) {
        logger.info("Get all directors from an Institute");
        List<DirectorDTO> response = new ArrayList<>(service.readAll());
        if (response.isEmpty()) {
            errorSearch();
            return ResponseEntity.badRequest()
                    .headers(header(NOT_FOUND, "There are no directors in the Institute")).body(null);
        }
        correctlySearch();
        return ResponseEntity.ok().headers(header(SUCCESFULL, FOUND)).body(response);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<DirectorDTO>> readAll() {
        logger.info("Get all directors");
        List<DirectorDTO> response = new ArrayList<>(service.readAll());
        if (response.isEmpty()) {
            errorSearch();
            return ResponseEntity.badRequest()
                    .headers(header(NOT_FOUND,"There are no directors in the database")).body(null);
        }
        correctlySearch();
        return ResponseEntity.ok().headers(header(SUCCESFULL, FOUND)).body(response);
    }

    @PutMapping(value = CUIT)
    public ResponseEntity<DirectorDTO> update(@PathVariable(value = "cuit") String cuit,
                                              @RequestBody DirectorDTO newDirector) {
        logger.info("Update director with cuit {}", cuit);
        DirectorDTO oldDirector = service.readByCuit(cuit);
        if (oldDirector == null) {
            logger.info("Update director - error");
            return ResponseEntity.badRequest().headers(header(NOT_FOUND, notFoundDescription(cuit))).body(null);
        }
        DirectorDTO response = service.update(newDirector);
        logger.info("Director updated");
        return ResponseEntity.ok().headers(header(SUCCESFULL, "Director updated")).body(response);
    }

    @DeleteMapping(value = CUIT)
    public ResponseEntity<DirectorDTO> disable(@PathVariable(value = "cuit") String cuit) {
        logger.info("Disabling director with cuit {}", cuit);
        DirectorDTO dto = service.readByCuit(cuit);
        if (dto == null) {
            logger.info("Disable director - error");
            return ResponseEntity.badRequest().headers(header(NOT_FOUND, notFoundDescription(cuit))).body(null);
        } else if (Boolean.FALSE.equals(dto.getEnabled())) {
            return ResponseEntity.badRequest()
                    .headers(header("DISABLE_ERROR", startDescription(cuit) + " is already disabled"))
                    .body(dto);
        }
        DirectorDTO response = service.disable(dto);
        logger.info("Director disabled");
        return ResponseEntity.ok().headers(header(SUCCESFULL, "Director disabled")).body(response);
    }

    @PatchMapping(value = CUIT)
    public ResponseEntity<DirectorDTO> enable(@PathVariable(value = "cuit") String cuit) {
        logger.info("Enabling director with cuit {}", cuit);
        DirectorDTO dto = service.readByCuit(cuit);
        if (dto == null) {
            logger.info("Enable director - error");
            return ResponseEntity.badRequest().headers(header(NOT_FOUND, notFoundDescription(cuit))).body(null);
        } else if (Boolean.TRUE.equals(dto.getEnabled())) {
            return ResponseEntity.badRequest()
                    .headers(header("ENABLE_ERROR", startDescription(cuit) + "is already enabled"))
                    .body(dto);
        }
        DirectorDTO response = service.enable(dto);
        logger.info("Director enabled");
        return ResponseEntity.ok().headers(header(SUCCESFULL, "Director enabled")).body(response);
    }

    private void correctlySearch() {
        logger.info(FOUND);
    }

    private void errorSearch() {
        logger.info(CONT_ERROR_SEARCH);
    }

    private HttpHeaders header(String headerName, String description) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, description);
        return headers;
    }

    private String startDescription(String cuit) {
        return DIRECTOR_WITH_CUIT + cuit;
    }

    private String notFoundDescription(String cuit) {
        return startDescription(cuit) + " does not exist";
    }

}

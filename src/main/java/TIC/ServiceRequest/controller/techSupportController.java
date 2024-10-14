package TIC.ServiceRequest.controller;

import TIC.ServiceRequest.dto.TechRequest;
import TIC.ServiceRequest.model.TechSupport;
import TIC.ServiceRequest.service.impl.TechSupportServiceImpl;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/support")
public class techSupportController {
    private  TechSupportServiceImpl service;
    protected static final Logger logger = LoggerFactory.getLogger(techSupportController.class);

    public techSupportController(TechSupportServiceImpl service) {
    this.service = service;
    }

    @PostMapping("/support")
    public ResponseEntity<TechSupport> createSupport(@RequestBody TechRequest techRequest){
        return new ResponseEntity(this.service.requestService(techRequest), HttpStatus.CREATED);
    }


}

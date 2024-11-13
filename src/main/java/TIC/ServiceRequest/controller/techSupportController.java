package TIC.ServiceRequest.controller;

import TIC.ServiceRequest.dto.TechSupportDTO;
import TIC.ServiceRequest.service.impl.TechSupportServiceImpl;
import jakarta.validation.Valid;
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
    private final TechSupportServiceImpl service;
    protected static final Logger logger = LoggerFactory.getLogger(techSupportController.class);

    public techSupportController(TechSupportServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/support")
    public ResponseEntity<TechSupportDTO> createSupport( @RequestBody TechSupportDTO techRequest) {
        return new ResponseEntity<>(this.service.requestService(techRequest), HttpStatus.CREATED);
    }


    @PostMapping("/schedule")
    public ResponseEntity<TechSupportDTO> scheduleSupport(@RequestBody TechSupportDTO techRequest){
        return new ResponseEntity<>(this.service.scheduleService(techRequest.getDate(),techRequest),HttpStatus.CREATED);
    }

    @PostMapping("/acceptTechnician/{id}")
    public ResponseEntity<TechSupportDTO> acceptTechnician (@PathVariable Long id) {
        return new ResponseEntity<>(this.service.acceptTechnician(id), HttpStatus.OK);
    }

    @PostMapping("/acceptDirector/{id}")
    public ResponseEntity<TechSupportDTO>acceptDirector(@PathVariable Long id){
        return new ResponseEntity<>(this.service.acceptDirector(id),HttpStatus.OK);
    }
    @PostMapping("/cenceledService/{id}")
    public ResponseEntity<TechSupportDTO> cenceledService (@PathVariable Long id){
        return new ResponseEntity<>(this.service.cenceledService(id),HttpStatus.OK);
    }
}

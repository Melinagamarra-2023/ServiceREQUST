package TIC.ServiceRequest.controller;

import TIC.ServiceRequest.dto.ScheduleRequest;
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
    public ResponseEntity<TechSupportDTO> createSupport(@Valid @RequestBody TechSupportDTO techRequest) {
        return new ResponseEntity<>(this.service.requestService(techRequest), HttpStatus.CREATED);
    }


    @PostMapping("/schedule")
    public ResponseEntity<TechSupportDTO> scheduleSupport(@Valid @RequestBody ScheduleRequest scheduleRequest){
        return new ResponseEntity<>(this.service.scheduleService(scheduleRequest.getDate(),scheduleRequest),HttpStatus.CREATED);
    }

    @PostMapping("/acceptTechnician")
    public ResponseEntity<TechSupportDTO> acceptTechnician (@Valid @RequestBody ScheduleRequest request) {
        return new ResponseEntity<>(this.service.acceptTechnician(request.getCode()), HttpStatus.OK);
    }

    @PostMapping("/acceptDirector")
    public ResponseEntity<TechSupportDTO>acceptDirector(@Valid @RequestBody ScheduleRequest request){
        return new ResponseEntity<>(this.service.acceptDirector(request.getCode()),HttpStatus.OK);
    }
    @PostMapping("/cenceledService")
    public ResponseEntity<TechSupportDTO> cenceledService (@Valid @RequestBody ScheduleRequest request){
        return new ResponseEntity<>(this.service.cenceledService(request.getCode()),HttpStatus.OK);
    }
}

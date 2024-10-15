package TIC.ServiceRequest.controller;

import TIC.ServiceRequest.dto.ScheduleRequest;
import TIC.ServiceRequest.dto.TechRequest;
import TIC.ServiceRequest.dto.TechResponse;
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
    private TechSupportServiceImpl service;
    protected static final Logger logger = LoggerFactory.getLogger(techSupportController.class);

    public techSupportController(TechSupportServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/support")
    public ResponseEntity<TechSupport> createSupport(@RequestBody TechRequest techRequest) {
        return new ResponseEntity(this.service.requestService(techRequest), HttpStatus.CREATED);
    }


    @PostMapping("/schedule")
    public ResponseEntity<TechSupport> scheduleSupport(@RequestBody ScheduleRequest scheduleRequest){
        return new ResponseEntity(this.service.scheduleService(scheduleRequest.getDate(),scheduleRequest),HttpStatus.CREATED);
    }

    @PostMapping("/acceptTechnician")
    public TechResponse acceptTechnician (@RequestBody ScheduleRequest request){
            return this.service.acceptTechnician(request.getCode());
    }
    @PostMapping("/acceptDirector")
    public TechResponse acceptDirector (@RequestBody ScheduleRequest request){
        return this.service.acceptDirector(request.getCode());
    }
    @PostMapping("/cenceledService")
    public TechResponse cenceledService (@RequestBody ScheduleRequest request){
        return this.service.cenceledService(request.getCode());
    }
}

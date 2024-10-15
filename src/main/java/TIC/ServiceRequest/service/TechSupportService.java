package TIC.ServiceRequest.service;

import TIC.ServiceRequest.dto.ScheduleRequest;
import TIC.ServiceRequest.dto.TechRequest;

import TIC.ServiceRequest.dto.TechResponse;


import java.util.GregorianCalendar;

public interface TechSupportService {

    TechResponse requestService(TechRequest requestTech);


   TechResponse scheduleService(GregorianCalendar date, ScheduleRequest request);

    TechResponse acceptTechnician(String code);
    TechResponse acceptDirector(String code);
    TechResponse  cenceledService(String code);


}

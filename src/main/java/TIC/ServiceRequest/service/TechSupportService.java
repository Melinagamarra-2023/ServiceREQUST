package TIC.ServiceRequest.service;

import TIC.ServiceRequest.dto.ScheduleRequest;

import TIC.ServiceRequest.dto.TechSupportDTO;


import java.util.GregorianCalendar;

public interface TechSupportService {

    TechSupportDTO requestService(TechSupportDTO requestTech);


   TechSupportDTO scheduleService(GregorianCalendar date, ScheduleRequest request);

    TechSupportDTO acceptTechnician(String code);
    TechSupportDTO acceptDirector(String code);
    TechSupportDTO cenceledService(String code);


}

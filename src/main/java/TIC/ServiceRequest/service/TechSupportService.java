package TIC.ServiceRequest.service;

import TIC.ServiceRequest.dto.TechSupportDTO;


import java.util.GregorianCalendar;

public interface TechSupportService {

    TechSupportDTO requestService(TechSupportDTO requestTech);


   TechSupportDTO scheduleService(GregorianCalendar date, TechSupportDTO request);

    TechSupportDTO acceptTechnician(Long id);
    TechSupportDTO acceptDirector(Long id);
    TechSupportDTO cenceledService(Long id);


}

package TIC.ServiceRequest.service;

import TIC.ServiceRequest.dto.TechRequest;

import TIC.ServiceRequest.dto.TechResponse;
import TIC.ServiceRequest.model.TechSupport;



import java.util.GregorianCalendar;

public interface TechSupportService {

    TechResponse requestService(TechRequest requestTech);

    void scheduleService(GregorianCalendar date, Long id);
    void acceptTechnician(String code);
    void acceptDirector(String code);
    void cenceledService(String code);

}

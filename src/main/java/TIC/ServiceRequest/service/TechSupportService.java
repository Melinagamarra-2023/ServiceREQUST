package TIC.ServiceRequest.service;

import TIC.ServiceRequest.dto.TechRequest;

import java.util.GregorianCalendar;

public interface TechSupportService {

    void requestService(TechRequest requestTech);
    void scheduleService(GregorianCalendar date, Long id);
    void acceptTechnician(String code);
    void acceptDirector(String code);
    void cenceledService(String code);

}

package TIC.ServiceRequest.service;

import TIC.ServiceRequest.dto.RequestTech;

import java.util.GregorianCalendar;

public interface TechSupportService {

    void requestService(RequestTech requestTech);
    void scheduleService(GregorianCalendar date, Long id);
    void acceptTechnician(String code);
    void acceptDirector(String code);


}

package TIC.ServiceRequest.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum State {
    SOLICITADO("Solicitado"),
    AGENDADO("Agendado"),
    CONFIRMADO("Confirmado"),
    COMPLETO("Completo"),
    CANCELADO("Cancelado"),
    ;
    String name;
}

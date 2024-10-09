package TIC.ServiceRequest.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum State {
    SOLICITADO("Solicitado"),
    AGENDADO("Agendado"),
    PRESENTE("Presente"),
    TERMINADO("TERMINADO"),
    CANCELADO("Cancelado"),
    ;
    String name;
}

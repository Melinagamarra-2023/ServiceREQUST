package TIC.ServiceRequest.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum State {
    SOLICITADO("Solicitado"),
    AGENDADO("Agendado"),
    PRESENTE("Presente"),
    TERMINADO("Terminado"),
    CANCELADO("Cancelado"),
    ;
    String name;
}

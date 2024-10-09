package TIC.ServiceRequest.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SupportType {
    ACTUALIZACION("Actualización"),
    ARREGLOS("Arreglos"),
    DESBLOQUEO("Desbloqueo"),
    CAMBIO_DE_COMPONENTES("Cambio de componentes"),
    CAPACITACION_DOCENTE("Capacitación destienada a docentes"),
    CAPACITACION_ALUMNO("Capacitación destinana a alumnos"),
    COLEGA_ADHERIDO("Acompañante de otro técnico"),
    EVENTO_ESPECIAL("Evento ajeno a un servicio técnico");
    String name;
}
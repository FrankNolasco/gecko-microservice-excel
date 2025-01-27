package com.geckofull.excelcodifier.models.api;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Turno {

    @JsonProperty("ID")
    private String ID;
    private int tipo;
    private String nombre;
    private int estado;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private LocalDateTime horaSUSALUD;
    private LocalDateTime horaFinSUSALUD;
    private int cantidadHoras;
    private String nombreTurno;

}

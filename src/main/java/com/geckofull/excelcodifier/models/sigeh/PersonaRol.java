package com.geckofull.excelcodifier.models.sigeh;

import com.geckofull.excelcodifier.models.Fila;
import com.geckofull.excelcodifier.models.Grupo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonaRol {
    // Getters y Setters
    private String servicio;
    private String anio;
    private String mes;
    private String nombreCompleto;
    private String numDocumento;
    private String cargo;
    private String condicionLaboral;
    private String observaciones;
    private List<Turnos> turnos;  // Lista de objetos Turnos
}



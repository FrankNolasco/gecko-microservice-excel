package com.geckofull.excelcodifier.models.sigeh;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Turnos {
    private String nombre;
    private List<Turno> turnos = new ArrayList<>();
}



package com.geckofull.excelcodifier.models.sigeh;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CabeceraRol {
    private String nombre;

    public CabeceraRol(String nombre) {
        this.nombre = nombre;
    }
}



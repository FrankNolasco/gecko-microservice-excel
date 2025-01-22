package com.geckofull.excelcodifier.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Hoja {
    private String nombre;
    private Map<String, Integer> rango;
    private List<Fila> datos;
}
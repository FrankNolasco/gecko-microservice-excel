package com.geckofull.excelcodifier.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TablaConCabecera {
    private String hoja;
    private Fila cabecera;
    private List<Fila> datos;
    private List<Grupo> grupos;
    private int limiteFilaInicio;
    private int limiteFilaFin;
    private int limiteColumnaInicio;
    private int limiteColumnaFin;

}
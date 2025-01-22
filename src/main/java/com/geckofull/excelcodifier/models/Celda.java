package com.geckofull.excelcodifier.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Celda {
    private int fila;
    private int columna;
    private Object valor;

    public Celda() {
    }

    public Celda(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.valor = "";
    }

}
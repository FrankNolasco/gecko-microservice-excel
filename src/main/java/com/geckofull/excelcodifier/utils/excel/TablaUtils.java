package com.geckofull.excelcodifier.utils.excel;

import com.geckofull.excelcodifier.models.Celda;
import com.geckofull.excelcodifier.models.Fila;

import java.util.Optional;

public class TablaUtils {

    // Metodo para obtener la celda en una fila por su índice de columna
    public static Optional<Celda> obtenerCeldaPorIndice(Fila fila, int indiceColumna) {
        // Busca la celda cuyo 'columna' coincida con 'indiceColumna'
        return fila.stream()
                .filter(celda -> celda.getColumna() == indiceColumna) // Asumiendo que 'getColumna()' devuelve el ID de la columna
                .findFirst();  // Devuelve la primera celda que coincida
    }


    // Metodo para obtener el índice de una columna en la cabecera
    public static int obtenerIndiceColumna(Fila cabecera, String nombreColumna) {
        for (Celda celda : cabecera) {
            if (nombreColumna.equals(celda.getValor())) {
                return celda.getColumna();
            }
        }
        return -1; // Retorna -1 si no se encuentra la columna
    }

    public static Celda obtenerCeldaPorNombreColumna(Fila fila, Fila cabecera, String nombreColumna) {
        int indiceColumna = obtenerIndiceColumna(cabecera, nombreColumna);
        int indiceFila = fila.get(0).getFila();  // Suponiendo que cada fila tiene un índice de fila
        // Intentamos obtener la celda
        return fila.stream()
                .filter(celda -> celda.getColumna() == indiceColumna)
                .findFirst()
                .orElse(new Celda(indiceFila, indiceColumna));  // Devuelve una celda vacía con fila y columna correctos
    }

}

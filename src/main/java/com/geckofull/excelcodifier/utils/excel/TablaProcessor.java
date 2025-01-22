package com.geckofull.excelcodifier.utils.excel;

import com.geckofull.excelcodifier.models.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TablaProcessor {

    public Map<String, Object> obtenerTablas(List<Hoja> hojas, String cabeceraPatron) {
        List<String> cabecera = Arrays.asList(cabeceraPatron.split(","));
        List<TablaConCabecera> tablas = new ArrayList<>();

        for (Hoja hoja : hojas) {
            List<Fila> datos = hoja.getDatos();
            List<Fila> tablaActual = new ArrayList<>();
            boolean tablaEnCurso = false;
            int filaInicio = -1;
            Fila cabeceraActual = null; // Variable para almacenar la cabecera actual

            for (int filaIndex = 0; filaIndex < datos.size(); filaIndex++) {
                Fila fila = datos.get(filaIndex);

                // Si encontramos la cabecera, comenzamos la tabla (si no ha comenzado ya)
                if (esCabecera(fila, cabecera) && !tablaEnCurso) {
                    cabeceraActual = fila; // Guardamos la fila como la cabecera
                    tablaActual = new ArrayList<>();
                    filaInicio = filaIndex + 1; // La siguiente fila contiene los datos
                    tablaEnCurso = true;
                }
                // Si estamos dentro de la tabla y la fila no es cabecera, la agregamos
                else if (tablaEnCurso && !esCabecera(fila, cabecera)) {
                    tablaActual.add(fila);
                }

                // Si encontramos una nueva cabecera y ya hay una tabla en curso, guardamos la anterior
                else if (esCabecera(fila, cabecera) && tablaEnCurso) {
                    // Guardamos la tabla anterior con la cabecera actual
                    agregarTabla(tablas, hoja, tablaActual, cabeceraActual, filaInicio, filaIndex);
                    cabeceraActual = fila; // Actualizamos la cabecera
                    tablaActual = new ArrayList<>();
                    filaInicio = filaIndex + 1;
                }
            }

            // Si al final hubo una tabla en curso, la agregamos
            if (tablaEnCurso) {
                agregarTabla(tablas, hoja, tablaActual, cabeceraActual, filaInicio, datos.size());
            }
        }

        Map<String, Object> resultadoTablas = new HashMap<>();
        resultadoTablas.put("tablas", tablas);
        return resultadoTablas;
    }

    private void agregarTabla(List<TablaConCabecera> tablas, Hoja hoja, List<Fila> tablaActual, Fila cabecera, int filaInicio, int filaFin) {
        if (!tablaActual.isEmpty()) {
            TablaConCabecera tablaInfo = new TablaConCabecera();
            tablaInfo.setHoja(hoja.getNombre());
            tablaInfo.setCabecera(cabecera); // Usamos la cabecera correcta
            tablaInfo.setDatos(tablaActual); // Los datos de la tabla
            tablaInfo.setLimiteFilaInicio(filaInicio);
            tablaInfo.setLimiteFilaFin(filaFin);
            tablaInfo.setLimiteColumnaInicio(0);
            tablaInfo.setLimiteColumnaFin(tablaInfo.getCabecera().size() - 1);
            tablas.add(tablaInfo);
        }
    }

    private boolean esCabecera(Fila fila, List<String> cabecera) {
        if (fila.size() < cabecera.size()) {
            return false;  // Si la fila tiene menos columnas que la cabecera, no es válida
        }

        // Comparar cada celda de la fila con los valores esperados de la cabecera
        for (int i = 0; i < cabecera.size(); i++) {
            String valorCabecera = cabecera.get(i);
            Object valorCelda = fila.get(i).getValor();
            if (!ComparadorUtil.compararValor(valorCabecera, valorCelda)) {
                return false;
            }
        }

        return true;
    }
}

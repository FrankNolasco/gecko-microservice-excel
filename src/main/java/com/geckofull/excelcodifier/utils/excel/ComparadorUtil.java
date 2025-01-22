package com.geckofull.excelcodifier.utils.excel;


public class ComparadorUtil {

    // Método estático para comparar el valor de la cabecera con el valor de la celda
    public static boolean compararValor(String valorCabecera, Object valorCelda) {
        String valorCeldaStr = String.valueOf(valorCelda);

        Double valorCeldaDouble = null;
        Double valorCabeceraDouble = null;

        try {
            valorCeldaDouble = Double.valueOf(valorCeldaStr);
        } catch (NumberFormatException ignored) {
        }

        try {
            valorCabeceraDouble = Double.valueOf(valorCabecera);
        } catch (NumberFormatException ignored) {
        }

        // Comparar como números si ambos son numéricos
        if (valorCeldaDouble != null && valorCabeceraDouble != null) {
            return valorCeldaDouble.equals(valorCabeceraDouble);
        }

        // Si al menos uno de los valores no es numérico, comparar como String
        return valorCabecera.equals(valorCeldaStr);
    }
}

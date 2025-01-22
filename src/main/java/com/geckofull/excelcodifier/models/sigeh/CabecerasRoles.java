package com.geckofull.excelcodifier.models.sigeh;

import java.util.Arrays;
import java.util.List;

public class CabecerasRoles {
    // Definir las cabeceras con su extensión
    public static final CabeceraRol SERVICIO = new CabeceraRol("SERVICIO");
    public static final CabeceraRol APELLIDOS_Y_NOMBRES = new CabeceraRol("APELLIDOS Y NOMBRES");
    public static final CabeceraRol MES = new CabeceraRol("MES");
    public static final CabeceraRol AÑO = new CabeceraRol("AÑO");
    public static final CabeceraRol CARGO = new CabeceraRol("CARGO");
    public static final CabeceraRol N_DOCUMENTO = new CabeceraRol("N° Documento");
    public static final CabeceraRol CONDICION_LABORAL = new CabeceraRol("CONDICION LABORAL");
    public static final CabeceraRol OBSERVACIONES = new CabeceraRol("OBSERVACIONES");

    // Lista de cabeceras
    public static final List<CabeceraRol> CABECERAS = Arrays.asList(
            SERVICIO, APELLIDOS_Y_NOMBRES, MES, AÑO, CARGO, N_DOCUMENTO, CONDICION_LABORAL, OBSERVACIONES
    );
}

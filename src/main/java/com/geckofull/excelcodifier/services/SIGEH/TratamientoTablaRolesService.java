package com.geckofull.excelcodifier.services.SIGEH;
import com.geckofull.excelcodifier.models.Fila;
import com.geckofull.excelcodifier.models.Grupo;
import com.geckofull.excelcodifier.models.TablaConCabecera;
import com.geckofull.excelcodifier.models.sigeh.TablaRoles;
import com.geckofull.excelcodifier.utils.excel.SIGEH.TratamientoTablaRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TratamientoTablaRolesService {

    private final TratamientoTablaRoles tratamientoTablas;

    @Autowired
    public TratamientoTablaRolesService(TratamientoTablaRoles tratamientoTablas) {
        // Inicializamos la clase TratamientoTablas que contiene las reglas de tratamiento
        this.tratamientoTablas = tratamientoTablas;
    }

    // Metodo central para realizar todo el tratamiento de depuración
    public List<TablaRoles> procesarTabla(List<TablaConCabecera> tablas) {
        //public List<TablaConCabecera> procesarTabla(List<TablaConCabecera> tablas) {
        try {
            List<TablaConCabecera> tablasConTratamiento = new ArrayList<TablaConCabecera>();

            for (TablaConCabecera tabla : tablas) {
                List<Fila> datosFiltrados = tratamientoTablas.depurarPorTurno(tabla.getDatos(), tabla.getCabecera());
                TablaConCabecera tablaDepurada = getTablaConCabecera(tabla, datosFiltrados);
                List<Grupo> datosAgrupados = tratamientoTablas.agruparPorTurno(tablaDepurada.getDatos(), tablaDepurada.getCabecera());
                tablaDepurada.setGrupos(datosAgrupados);
                tablasConTratamiento.add(tablaDepurada);
            }
            return tratamientoTablas.generarTablaRoles(tablasConTratamiento);
            //return tablasConTratamiento;
        }   catch (Exception e) {
            return null;
        }
    }

    private static TablaConCabecera getTablaConCabecera(TablaConCabecera tabla, List<Fila> datosFiltrados) {
        TablaConCabecera tablaDepurada = new TablaConCabecera();
        tablaDepurada.setHoja(tabla.getHoja());
        tablaDepurada.setCabecera(tabla.getCabecera());
        tablaDepurada.setDatos(datosFiltrados);
        tablaDepurada.setLimiteColumnaFin(tabla.getLimiteColumnaFin());
        tablaDepurada.setLimiteColumnaInicio(tabla.getLimiteColumnaInicio());
        tablaDepurada.setLimiteFilaFin(tabla.getLimiteFilaFin());
        tablaDepurada.setLimiteFilaInicio(tabla.getLimiteFilaInicio());
        return tablaDepurada;
    }
}


package com.geckofull.excelcodifier.utils.excel.SIGEH;

import com.geckofull.excelcodifier.models.Celda;
import com.geckofull.excelcodifier.models.Fila;
import com.geckofull.excelcodifier.models.Grupo;
import com.geckofull.excelcodifier.models.TablaConCabecera;
import com.geckofull.excelcodifier.models.sigeh.PersonaRol;
import com.geckofull.excelcodifier.models.sigeh.TablaRoles;
import com.geckofull.excelcodifier.models.sigeh.Turno;
import com.geckofull.excelcodifier.models.sigeh.Turnos;
import com.geckofull.excelcodifier.services.TurnoService;
import com.geckofull.excelcodifier.utils.excel.TablaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TratamientoTablaRoles {

    private final TurnoService turnoService;

    @Autowired
    public TratamientoTablaRoles(TurnoService turnoService) {
        this.turnoService = turnoService;
    }


    // TODO: Método para depurar las filas que no tienen valor en la columna "TURNO"
    public List<Fila> depurarPorTurno(List<Fila> tabla, Fila cabecera) {
        int indiceTurno = TablaUtils.obtenerIndiceColumna(cabecera, "TURNO");
        if (indiceTurno == -1) {
            return tabla;
        }
        /* Filtramos las filas que tienen valor en la columna "TURNO" */
        List<Fila> tablaFiltrada = new ArrayList<Fila>();
        for (Fila fila : tabla) {
            TablaUtils.obtenerCeldaPorIndice(fila, indiceTurno)
                    .filter(celda -> Objects.nonNull(celda.getValor()) && !celda.getValor().toString().isEmpty())
                    .ifPresent(celda -> tablaFiltrada.add(fila));
        }
        return tablaFiltrada;
    }

    public List<Grupo> agruparPorTurno(List<Fila> tabla, Fila cabecera) {
        int indiceTurno = TablaUtils.obtenerIndiceColumna(cabecera, "TURNO");
        if (indiceTurno == -1) {
            return new ArrayList<Grupo>();
        }

        List<Grupo> grupos = new ArrayList<>();
        final Grupo[] grupoActual = {null};  // Usamos un array para permitir su modificación

        for (Fila fila : tabla) {
            TablaUtils.obtenerCeldaPorIndice(fila, indiceTurno)
                    .ifPresent(celda -> {
                        // Agrupar de 3 en 3 en orden de aparición, por filas
                        if (grupoActual[0] == null || grupoActual[0].size() == 3) {
                            grupoActual[0] = new Grupo(); // Asignamos un nuevo grupo
                            grupos.add(grupoActual[0]);
                        }
                        grupoActual[0].add(fila);  // Añadir la fila al grupo actual
                    });
        }
        return grupos;
    }

    private String obtenerValor(Fila filaPersona, Fila cabecera, String columna) {
        return TablaUtils.obtenerCeldaPorNombreColumna(filaPersona, cabecera, columna).getValor().toString();
    }


    public List<PersonaRol> obtenerPersonasRoles(List<Grupo> grupos, Fila cabecera) {
        List<PersonaRol> personasRoles = new ArrayList<>();

        for (Grupo grupo : grupos) { // Recorremos los grupos
            // cada grupo tiene 3 filas
            // la primera fila tiene los datos de la persona

            Fila filaPersona = grupo.get(0);
            PersonaRol personaRol = new PersonaRol();
            personaRol.setServicio(obtenerValor(filaPersona, cabecera, "SERVICIO"));
            personaRol.setNombreCompleto(obtenerValor(filaPersona, cabecera, "APELLIDOS Y NOMBRES"));
            personaRol.setMes(obtenerValor(filaPersona, cabecera, "MES"));
            personaRol.setAnio(obtenerValor(filaPersona, cabecera, "AÑO"));
            personaRol.setCargo(obtenerValor(filaPersona, cabecera, "CARGO"));
            String numDocumento = obtenerValor(filaPersona, cabecera, "N° Documento");
            // Completar con ceros a la izquierda si la longitud del número de documento es menor a 8
            if (numDocumento.length() < 8) {
                numDocumento = String.format("%0" + (8 - numDocumento.length()) + "d%s", 0, numDocumento);
            }
            personaRol.setNumDocumento(numDocumento);
            personaRol.setNumDocumento(numDocumento);
            personaRol.setCondicionLaboral(obtenerValor(filaPersona, cabecera, "CONDICION LABORAL"));
            personaRol.setObservaciones(obtenerValor(filaPersona, cabecera, "OBSERVACIONES"));

            // Para agregar los turnos es necesario recorrer las 3 filas del grupo
            // los turnos son todos los valores de las filas cuyo índice de columna corresponde a una cabecera cuyo valor es de tipo double
            List<Turnos> turnos = new ArrayList<>();

            for (Fila fila : grupo) {
                // Fila filaTurno = new Fila();
                Turnos turnosPadre = new Turnos();
                List<Turno> turnosHijo = new ArrayList<>();
                String turnoPadre = TablaUtils.obtenerCeldaPorNombreColumna(fila, cabecera, "TURNO").getValor().toString();
                turnosPadre.setNombre(turnoPadre);
                for (Celda caberaCelda : cabecera) {
                    if (caberaCelda.getValor() instanceof Double) {
                        Celda celdaTurno = TablaUtils.obtenerCeldaPorIndice(fila, caberaCelda.getColumna())
                                .orElse(new Celda(fila.get(0).getFila(), caberaCelda.getColumna()));
                        int dia = Integer.parseInt(caberaCelda.getValor().toString().replace(".0", "")); // Convertir a entero

                        Turno turno = new Turno();
                        turno.setDia(String.valueOf(dia));

                        List<String> turnosDisponibles = turnoService.listarTurnosDisponibles();
                        // Obtener el valor de celdaTurno
                        String valorTurno = celdaTurno.getValor().toString();
                        // Verificar si el valor es "m"
                        if ("m".equals(valorTurno)) {
                            // Si es "m", asignamos "m."
                            turno.setValor("m.");
                        } else {
                            // Si no es "m", verificamos si está en la lista de turnos disponibles
                            if (turnosDisponibles.contains(valorTurno)) {
                                // Si el valor existe en la lista, lo asignamos
                                turno.setValor(valorTurno);
                            } else {
                                // Si no existe en la lista, asignamos un valor vacío
                                turno.setValor("");
                            }
                        }
                        turnosHijo.add(turno);
                    }
                }
                turnosPadre.setTurnos(turnosHijo);
                // turnos.add(filaTurno);
                turnos.add(turnosPadre);
            }

            personaRol.setTurnos(turnos);

            personasRoles.add(personaRol);
        }
        return personasRoles;
    }

    public List<TablaRoles> generarTablaRoles(List<TablaConCabecera> tablasConCabeceras) {

        Map<String, TablaRoles> tablasPorHoja = new HashMap<>();

        for (TablaConCabecera tablaConCabecera : tablasConCabeceras) {
            String hoja = tablaConCabecera.getHoja();
            List<Grupo> grupos = tablaConCabecera.getGrupos();
            List<PersonaRol> personasRoles = obtenerPersonasRoles(grupos, tablaConCabecera.getCabecera());

            // Si ya existe una TablaRoles para esta hoja, combinar personas
            if (tablasPorHoja.containsKey(hoja)) {
                TablaRoles tablaExistente = tablasPorHoja.get(hoja);
                tablaExistente.getPersonas().addAll(personasRoles); // Unir personas
            } else {
                // Crear una nueva TablaRoles para esta hoja
                TablaRoles nuevaTabla = new TablaRoles();
                nuevaTabla.setHoja(hoja);
                nuevaTabla.setPersonas(new ArrayList<>(personasRoles));
                nuevaTabla.setId(tablasPorHoja.size()); // ID basado en el tamaño actual del mapa
                nuevaTabla.setEsValida(tablaConCabecera.getDatos().size() % 3 == 0);
                tablasPorHoja.put(hoja, nuevaTabla);
            }
        }

        // Convertir el mapa a lista y ordenar por ID
        List<TablaRoles> tablasRoles = new ArrayList<>(tablasPorHoja.values());
        tablasRoles.sort(Comparator.comparingInt(TablaRoles::getId));

        return tablasRoles;
    }



}

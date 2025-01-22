package com.geckofull.excelcodifier.controllers;

import com.geckofull.excelcodifier.models.TablaConCabecera;
import com.geckofull.excelcodifier.services.SIGEH.TratamientoTablaRolesService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.geckofull.excelcodifier.services.ExcelService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    private final ExcelService excelService;
    private final TratamientoTablaRolesService rolesService;
    @Autowired
    public ExcelController(ExcelService excelService, TratamientoTablaRolesService rolesService) {
        this.excelService = excelService;
        this.rolesService = rolesService;
    }

    @PostMapping("/procesar")
    public ResponseEntity<?> procesarExcel(@RequestParam("file") MultipartFile file) {
        try {
            // Llamar al servicio para procesar el archivo Excel
            var resultado = excelService.procesarArchivoExcel(file);
            return ResponseEntity.ok(resultado);  // Retorna una respuesta exitosa
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al procesar el archivo.");
        }
    }

    @PostMapping("/obtenerTablas")
    public ResponseEntity<?> obtenerTablas(@RequestParam("file") MultipartFile file, @RequestParam("cabeceraPatron") String cabeceraPatron) {
        try {
            // Llamar al servicio para obtener las tablas del archivo Excel
            System.out.println("Ingresa en controlador:: obtener tablas");
            var resultado = excelService.obtenerTablasExcel(file, cabeceraPatron);
            return ResponseEntity.ok(resultado);  // Retorna una respuesta exitosa
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body("Error al obtener las tablas del archivo.");
        }
    }

    @PostMapping("/tratamientoTablaRoles")
    public ResponseEntity<?> tratamientoTablaRoles(@RequestParam("file") MultipartFile file, @RequestParam("cabeceraPatron") String cabeceraPatron) {
        try {
            // Llamar al servicio para obtener las tablas del archivo Excel
            System.out.println("Ingresa en controlador:: obtener tablas");
            Map<String, Object> tablasObj = excelService.obtenerTablasExcel(file, cabeceraPatron);
            var resultado = rolesService.procesarTabla((List<TablaConCabecera>) tablasObj.get("tablas"));
            return ResponseEntity.ok(resultado);  // Retorna una respuesta exitosa
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener las tablas del archivo.");
        }
    }
}


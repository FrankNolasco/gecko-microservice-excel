package com.geckofull.excelcodifier.controllers;

import com.geckofull.excelcodifier.services.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/validadores")
public class ValidadoresController {
    private final TurnoService turnoService;

    @Autowired
    public ValidadoresController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping("/turnos/test")
    public ResponseEntity<?> testTurnos() {
        try {
            var resultado = turnoService.listarTurnosDisponibles();
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al obtener las tablas del archivo.");
        }
    }
}



package com.geckofull.excelcodifier.services;

import com.geckofull.excelcodifier.config.ApiConfig;
import com.geckofull.excelcodifier.models.api.ApiResponse;
import com.geckofull.excelcodifier.models.api.Turno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TurnoService {

    private final ApiService apiService;
    private final ApiConfig apiConfig;

    private static List<String> turnosDisponiblesCache = null;
    private static long lastFetchTime = 0;
    private static final long CACHE_EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 5 minutos

    @Autowired
    public TurnoService(ApiService apiService, ApiConfig apiConfig) {
        this.apiService = apiService;
        this.apiConfig = apiConfig;
    }

    public List<String> listarTurnosDisponibles() {
        // Verifica si el cache aún es válido
        if (turnosDisponiblesCache == null || isCacheExpired()) {
            // Si no hay datos en cache o el cache expiró, realiza la llamada a la API
            turnosDisponiblesCache = obtenerTurnosDesdeApi();
        }
        return turnosDisponiblesCache;
    }

    private boolean isCacheExpired() {
        // Si el tiempo desde la última actualización es mayor que el tiempo de expiración, el cache ha expirado
        return System.currentTimeMillis() - lastFetchTime > CACHE_EXPIRATION_TIME;
    }

    private List<String> obtenerTurnosDesdeApi() {
        String url = apiConfig.getApiConsumerUrl();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("method", "POST");
        parametros.put("route", "/api/v1/personal/listar-variables-disponibles");

        // Usar el método genérico para obtener la respuesta
        ApiResponse apiResponse = apiService.hacerSolicitud(url, HttpMethod.POST, parametros, ApiResponse.class);

        if (apiResponse == null || apiResponse.getData() == null) {
            apiResponse = new ApiResponse();
            apiResponse.setData(new ArrayList<>());
        }

        List<String> turnosDisponibles = new ArrayList<>();
        for (Turno turno : apiResponse.getData()) {
            turnosDisponibles.add(turno.getID());
        }

        // Actualiza el tiempo de la última obtención de datos
        lastFetchTime = System.currentTimeMillis();

        return turnosDisponibles;
    }
}


package com.geckofull.excelcodifier.services;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Método genérico para manejar solicitudes HTTP
    public <T> T hacerSolicitud(String url, HttpMethod metodo, Object parametros, Class<T> claseRespuesta) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        HttpEntity<Object> entity = new HttpEntity<>(parametros, headers);

        try {
            // Realizar la solicitud
            ResponseEntity<T> response = restTemplate.exchange(url, metodo, entity, claseRespuesta);
            if (response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("La respuesta es nula");
            }
        } catch (Exception e) {
            System.err.println("Error en la solicitud: " + e.getMessage());
            return null;
        }
    }
}

package com.geckofull.excelcodifier.config;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Value("${api.token_part_1}")
    private String apiTokenPart1;

    @Value("${api.token_part_2}")
    private String apiTokenPart2;

    @Bean
    public RestTemplate restTemplate() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        String apiToken = apiTokenPart1.trim() + " " + apiTokenPart2.trim();
        interceptors.add(new TokenInterceptor(apiToken));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
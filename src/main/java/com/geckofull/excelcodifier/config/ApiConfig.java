package com.geckofull.excelcodifier.config;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiConfig {

    @Getter
    @Value("${api.base_url}")
    private String apiBaseUrl;

    @Value("${api.consumer_url}")
    private String apiConsumerUrl;

    public String getApiConsumerUrl() {
        return apiBaseUrl + apiConsumerUrl;
    }

}

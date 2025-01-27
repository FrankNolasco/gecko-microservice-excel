package com.geckofull.excelcodifier.models.api;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiResponse {
    private boolean error;
    private String message;
    private List<Turno> data;
}

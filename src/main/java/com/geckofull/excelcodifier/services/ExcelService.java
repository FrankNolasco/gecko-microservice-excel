// src/main/java/com/geckofull/excelcodifier/services/ExcelService.java
package com.geckofull.excelcodifier.services;

import com.geckofull.excelcodifier.models.Hoja;
import com.geckofull.excelcodifier.utils.excel.ExcelReader;
import com.geckofull.excelcodifier.utils.excel.TablaProcessor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {

    private final ExcelReader excelReader;
    private final TablaProcessor tablaProcessor;

    public ExcelService(ExcelReader excelReader, TablaProcessor tablaProcessor) {
        this.excelReader = excelReader;
        this.tablaProcessor = tablaProcessor;
    }

    public Map<String, Object> procesarArchivoExcel(MultipartFile file) throws IOException {
        return excelReader.procesarArchivo(file);
    }

    public Map<String, Object> obtenerTablasExcel(MultipartFile file, String cabeceraPatron) throws IOException {
        Map<String, Object> resultado = excelReader.procesarArchivo(file);
        List<Hoja> hojas = (List<Hoja>) resultado.get("hojas");
        return tablaProcessor.obtenerTablas(hojas, cabeceraPatron);
    }

}

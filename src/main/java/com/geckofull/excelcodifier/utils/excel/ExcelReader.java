package com.geckofull.excelcodifier.utils.excel;

import com.geckofull.excelcodifier.models.Celda;
import com.geckofull.excelcodifier.models.Fila;
import com.geckofull.excelcodifier.models.Hoja;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Component
public class ExcelReader {

    public Map<String, Object> procesarArchivo(MultipartFile file) throws IOException {
        List<Hoja> hojas = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                hojas.add(procesarHoja(sheet));
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("hojas", hojas);
        return result;
    }

    private Hoja procesarHoja(Sheet sheet) {
        String nombreHoja = sheet.getSheetName();
        Iterator<Row> rowIterator = sheet.iterator();

        List<Fila> hojaDatos = new ArrayList<>();
        int filaInicio = Integer.MAX_VALUE, filaFin = Integer.MIN_VALUE;
        int columnaInicio = Integer.MAX_VALUE, columnaFin = Integer.MIN_VALUE;

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Fila filaDatos = procesarFila(row, filaInicio, filaFin, columnaInicio, columnaFin);
            if (!filaDatos.isEmpty()) {
                hojaDatos.add(filaDatos);
            }
        }

        Hoja hoja = new Hoja();
        hoja.setNombre(nombreHoja);
        hoja.setRango(Map.of(
                "filaInicio", filaInicio,
                "columnaInicio", columnaInicio,
                "filaFin", filaFin,
                "columnaFin", columnaFin
        ));
        hoja.setDatos(hojaDatos);

        return hoja;
    }

    private Fila procesarFila(Row row, int filaInicio, int filaFin, int columnaInicio, int columnaFin) {
        Fila filaDatos = new Fila();
        Iterator<Cell> cellIterator = row.cellIterator();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getCellType() == CellType.BLANK) continue;

            Celda celda = new Celda();
            celda.setFila(row.getRowNum());
            celda.setColumna(cell.getColumnIndex());
            celda.setValor(obtenerValorCelda(cell));

            filaDatos.add(celda);

            filaInicio = Math.min(filaInicio, row.getRowNum());
            filaFin = Math.max(filaFin, row.getRowNum());
            columnaInicio = Math.min(columnaInicio, cell.getColumnIndex());
            columnaFin = Math.max(columnaFin, cell.getColumnIndex());
        }

        return filaDatos;
    }

    private Object obtenerValorCelda(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> cell.getNumericCellValue();
            default -> "";
        };
    }
}

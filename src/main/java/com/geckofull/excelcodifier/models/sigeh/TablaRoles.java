package com.geckofull.excelcodifier.models.sigeh;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TablaRoles {
    private String hoja;
    private int id;
    private List<PersonaRol> personas;
    private boolean esValida;
}
package com.banco.cuentasservice.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Tipo {
    AHORROS("Ahorros"),
    CORRIENTE("Corriente");

    private final String tipoString;

    Tipo(String tipoString) {
        this.tipoString = tipoString;
    }

    @JsonValue
    public String getTipoString() {
        return tipoString;
    }

    @JsonCreator
    public static Tipo fromString(String tipoString) {
        for (Tipo tipo : Tipo.values()) {
            if (tipo.tipoString.equalsIgnoreCase(tipoString)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se encontró un Tipo con el nombre: " + tipoString);
    }
}
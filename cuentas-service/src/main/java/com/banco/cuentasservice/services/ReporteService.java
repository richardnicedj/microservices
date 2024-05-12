package com.banco.cuentasservice.services;

import com.banco.cuentasservice.model.dtos.ClienteDTO;
import com.banco.cuentasservice.model.dtos.ReporteEstadoCuentaDTO;
import com.banco.cuentasservice.model.entities.Cuenta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {
    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private MovimientoService movimientoService;

    public String generarReporteEstadoCuenta(LocalDate fechaInicio, LocalDate fechaFin, ClienteDTO clienteRequest) {
        List<Cuenta> cuentas = cuentaService.obtenerCuentasPorCliente(clienteRequest.getNombre());
        List<ReporteEstadoCuentaDTO> movimientos = new ArrayList<>();
        for (Cuenta cuenta : cuentas) {
            movimientos.addAll(movimientoService.obtenerMovimientosPorCuentaYRangoFecha(cuenta, fechaInicio, fechaFin));
        }
        List<String> reporteJson = movimientos.stream()
                .map(ReporteEstadoCuentaDTO::toJson)
                .collect(Collectors.toList());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<JsonObject> jsonObjects = reporteJson.stream()
                .map(json -> gson.fromJson(json, JsonObject.class))
                .collect(Collectors.toList());

        String formattedJson = gson.toJson(jsonObjects);
        return formattedJson;
    }
}

package com.banco.cuentasservice.controllers;

import com.banco.cuentasservice.model.dtos.ClienteDTO;
import com.banco.cuentasservice.services.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> generarReporteEstadoCuenta(
        @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
        @RequestBody ClienteDTO clienteRequest) {
        String reporte = reporteService.generarReporteEstadoCuenta(fechaInicio, fechaFin, clienteRequest);
       return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(reporte);
    }
}

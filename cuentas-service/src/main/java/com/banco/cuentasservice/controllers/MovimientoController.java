package com.banco.cuentasservice.controllers;

import com.banco.cuentasservice.exceptions.SaldoNoDisponibleException;
import com.banco.cuentasservice.model.dtos.MovimientoDTO;
import com.banco.cuentasservice.services.MovimientoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoDTO addMovimiento(@RequestBody MovimientoDTO movimientoDTO) {
        try {
            return movimientoService.addMovimiento(movimientoDTO);
        } catch (ResponseStatusException e) {
            throw e; // Si ya es una excepción de estado, relanzarla tal cual
        } catch (SaldoNoDisponibleException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo no disponible", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", e);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<MovimientoDTO> getAllMovimientos() {
        try {
            return movimientoService.getAllMovimientos();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MovimientoDTO getMovimientoById(@PathVariable Long id) {
        try {
            return movimientoService.getMovimientoById(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimiento no encontrado", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al obtener un movimiento", e);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public MovimientoDTO updateMovimiento(@RequestBody MovimientoDTO movimientoDTO) {
        try {
            return movimientoService.updateMovimiento(movimientoDTO);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimiento no encontrado", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al actualizar un movimiento", e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMovimiento(@PathVariable Long id) {
        try {
            movimientoService.deleteMovimiento(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimiento no encontrado", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al eliminar un movimiento", e);
        }
    }
}

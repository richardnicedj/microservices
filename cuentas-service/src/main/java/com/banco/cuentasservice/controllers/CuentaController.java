package com.banco.cuentasservice.controllers;

import com.banco.cuentasservice.model.dtos.CuentaDTO;
import com.banco.cuentasservice.services.CuentaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<CuentaDTO> getAllCuentas() {
        try {
            return cuentaService.getAllCuentas();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CuentaDTO getCuentaById(@PathVariable Long id) {
        try {
            return cuentaService.getCuentaById(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaDTO addCuenta(@RequestBody CuentaDTO cuentaRequest) {
        try {
            return cuentaService.addCuenta(cuentaRequest);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCuenta(@PathVariable Long id) {
        try {
            cuentaService.deleteCuenta(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al eliminar una cuenta", e);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CuentaDTO updateCuenta(@RequestBody CuentaDTO cuentaRequest) {
        try {
            return cuentaService.updateCuenta(cuentaRequest);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al actualizar una cuenta", e);
        }
    }

    @GetMapping("/numero/{numeroCuenta}")
    @ResponseStatus(HttpStatus.OK)
    public CuentaDTO getCuentaByNumeroCuenta(@PathVariable String numeroCuenta) {
        try {
            return cuentaService.getCuentaByNumeroCuenta(numeroCuenta);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada", e);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,  "Error inesperado al consultar la cuenta", e);
        }
    }
}

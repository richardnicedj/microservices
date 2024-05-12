package com.banco.clientes_service.controllers;

import com.banco.clientes_service.model.dtos.ClienteDTO;
import com.banco.clientes_service.services.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCliente(@RequestBody ClienteDTO clienteRequest) {
        try {
            this.clienteService.addCliente(clienteRequest);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al agregar un cliente", ex);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteDTO> getAllClientes() {
        return this.clienteService.getAllClientes();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO getClienteById(@PathVariable Long id) {
        try {
            return this.clienteService.getClienteById(id);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al obtener un cliente", ex);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClienteById(@PathVariable Long id) {
        try {
            this.clienteService.deleteClienteById(id);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al eliminar un cliente", ex);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateCliente(@RequestBody ClienteDTO clienteRequest) {
        try {
            this.clienteService.updateCliente(clienteRequest);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al actualizar un cliente", ex);
        }
    }
}

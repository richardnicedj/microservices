package com.banco.clientes_service.services;

import com.banco.clientes_service.model.dtos.ClienteDTO;
import com.banco.clientes_service.model.entities.Cliente;
import com.banco.clientes_service.model.entities.Persona;
import com.banco.clientes_service.repositories.ClienteRepository;
import com.banco.clientes_service.repositories.PersonaRepository;
import com.banco.clientes_service.utils.Status;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;

    public void addCliente(ClienteDTO clienteRequest) {
        try {
            var persona = Persona.builder()
                    .nombre(clienteRequest.getNombre())
                    .genero(clienteRequest.getGenero())
                    .edad(clienteRequest.getEdad())
                    .identificacion(clienteRequest.getIdentificacion())
                    .direccion(clienteRequest.getDireccion())
                    .telefono(clienteRequest.getTelefono())
                    .status(Status.ACTIVE)
                    .build();

            Persona personaInDb = personaRepository.save(persona);

            var cliente = Cliente.builder()
                    .contrasena(clienteRequest.getContrasena())
                    .estado(clienteRequest.getEstado())
                    .status(Status.ACTIVE)
                    .persona(personaInDb)
                    .build();

            clienteRepository.save(cliente);

            log.info("Se agregó el cliente {}", cliente);
        } catch (DataAccessException ex) {
            log.error("Error al agregar un cliente a la base de datos: {}", ex.getMessage());
            throw new RuntimeException("Error al agregar un cliente a la base de datos", ex);
        } catch (Exception ex) {
            log.error("Error inesperado al agregar un cliente: {}", ex.getMessage());
            throw new RuntimeException("Error inesperado al agregar un cliente", ex);
        }
    }


    public List<ClienteDTO> getAllClientes() {
        try {
            var clientes = clienteRepository.findAllActiveClients();
            log.info("Se encontraron {} clientes", clientes.size());
            return clientes.stream().map(this::mapToClienteDTO).toList();
        } catch (DataAccessException ex) {
            log.error("Error al obtener todos los clientes de la base de datos: {}", ex.getMessage());
            throw new RuntimeException("Error al obtener todos los clientes de la base de datos", ex);
        } catch (Exception ex) {
            log.error("Error inesperado al obtener todos los clientes: {}", ex.getMessage());
            throw new RuntimeException("Error inesperado al obtener todos los clientes", ex);
        }
    }

    private ClienteDTO mapToClienteDTO(Cliente cliente) {
        Persona persona = cliente.getPersona();

        return ClienteDTO.builder()
                .clienteId(cliente.getClienteId())
                .nombre(persona.getNombre())
                .genero(persona.getGenero())
                .edad(persona.getEdad())
                .identificacion(persona.getIdentificacion())
                .direccion(persona.getDireccion())
                .telefono(persona.getTelefono())
                .contrasena(cliente.getContrasena())
                .estado(cliente.getEstado())
                .build();
    }

    public ClienteDTO getClienteById(Long id) {
        try {
            var cliente = clienteRepository.findActiveById(id).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID: " + id));
            log.info("Se encontro el cliente {}", cliente);
            return mapToClienteDTO(cliente);
        } catch (EntityNotFoundException ex) {
            log.error("Cliente no encontrado con ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Cliente no encontrado", ex);
        } catch (DataAccessException ex) {
            log.error("Error al obtener un cliente de la base de datos con ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error al obtener un cliente de la base de datos", ex);
        } catch (Exception ex) {
            log.error("Error inesperado al obtener un cliente con ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error inesperado al obtener un cliente", ex);
        }
    }

    public void deleteClienteById(Long id) {
        try {
            var cliente = clienteRepository.findActiveById(id).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID: " + id));
            cliente.setStatus(Status.DELETED);
            clienteRepository.save(cliente);
            Persona persona = cliente.getPersona();
            persona.setStatus(Status.DELETED);
            personaRepository.save(persona);
            log.info("Se eliminó el cliente {}", cliente);
        } catch (EntityNotFoundException ex) {
            log.error("Cliente no encontrado con ID {}: {}", id, ex.getMessage());
            throw ex;
        } catch (DataAccessException ex) {
            log.error("Error al eliminar un cliente de la base de datos con ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error al eliminar un cliente de la base de datos", ex);
        } catch (Exception ex) {
            log.error("Error inesperado al eliminar un cliente con ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Error inesperado al eliminar un cliente", ex);
        }
    }



    public void updateCliente(ClienteDTO clienteRequest) {
        try {
            var cliente = clienteRepository.findActiveById(clienteRequest.getClienteId()).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID: " + clienteRequest.getClienteId()));
            Persona persona = cliente.getPersona();
            var personaInDb = personaRepository.findById(persona.getPersonaId()).orElseThrow(() -> new EntityNotFoundException("Persona no encontrada con ID: " + persona.getPersonaId()));
            personaInDb.setNombre(clienteRequest.getNombre());
            personaInDb.setGenero(clienteRequest.getGenero());
            personaInDb.setEdad(clienteRequest.getEdad());
            personaInDb.setIdentificacion(clienteRequest.getIdentificacion());
            personaInDb.setDireccion(clienteRequest.getDireccion());
            personaInDb.setTelefono(clienteRequest.getTelefono());
            personaRepository.save(personaInDb);
            cliente.setContrasena(clienteRequest.getContrasena());
            cliente.setEstado(clienteRequest.getEstado());
            clienteRepository.save(cliente);
            log.info("Se actualizo el cliente {}", cliente);
        } catch (EntityNotFoundException ex) {
            log.error("Cliente no encontrado con ID {}: {}", clienteRequest.getClienteId(), ex.getMessage());
            throw new RuntimeException("Cliente no encontrado", ex);
        } catch (DataAccessException ex) {
            log.error("Error al actualizar un cliente en la base de datos con ID {}: {}", clienteRequest.getClienteId(), ex.getMessage());
            throw new RuntimeException("Error al actualizar un cliente en la base de datos", ex);
        } catch (Exception ex) {
            log.error("Error inesperado al actualizar un cliente con ID {}: {}", clienteRequest.getClienteId(), ex.getMessage());
            throw new RuntimeException("Error inesperado al actualizar un cliente", ex);
        }
    }
}

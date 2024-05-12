package com.banco.clientes_service.model.entities;

import com.banco.clientes_service.utils.Status;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClienteTest {

    @Test
    public void testClienteCreation() {
        Long clienteId = 1L;
        String contrasena = "password";
        Boolean estado = true;
        Persona persona = new Persona(1L, "John", "Male", 30, "1234567890", "123 Main St", "555-555-5555", Status.ACTIVE);
        Status status = Status.ACTIVE;

        Cliente cliente = new Cliente(clienteId, contrasena, estado, persona, Status.ACTIVE);

        assertNotNull(cliente);
        assertEquals(clienteId, cliente.getClienteId());
        assertEquals(contrasena, cliente.getContrasena());
        assertEquals(estado, cliente.getEstado());
        assertEquals(persona, cliente.getPersona());
        assertEquals(status, cliente.getStatus());
    }
}


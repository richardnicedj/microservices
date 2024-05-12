package com.banco.clientes_service.controllers;

import com.banco.clientes_service.model.dtos.ClienteDTO;
import com.banco.clientes_service.services.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    public void getClienteById_WithValidId_ReturnsCliente() throws Exception {
        // Mocking the service method
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setClienteId(1L);
        clienteDTO.setNombre("John Doe");
        clienteDTO.setGenero("Masculino");
        clienteDTO.setEdad(30);
        clienteDTO.setIdentificacion("123456789");
        clienteDTO.setDireccion("123 Main St");
        clienteDTO.setTelefono("555-555-5555");
        clienteDTO.setContrasena("password");
        clienteDTO.setEstado(true);
        when(clienteService.getClienteById(1L)).thenReturn(clienteDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clienteId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genero").value("Masculino"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.edad").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.identificacion").value("123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.direccion").value("123 Main St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefono").value("555-555-5555"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contrasena").value("password"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estado").value(true));

    }

    @Test
    void getClienteById_WithInvalidId_ReturnsNotFound() throws Exception {
        when(clienteService.getClienteById(anyLong())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clientes/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteCliente_WithValidId_ReturnsNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteCliente_WithInvalidId_ReturnsNotFound() throws Exception {
        doThrow(new EntityNotFoundException()).when(clienteService).deleteClienteById(999L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clientes/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void updateCliente_WithValidId_ReturnsNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"clienteId\": 1, \"nombre\": \"John Doe\", \"genero\": \"Masculino\", \"edad\": 30, \"direccion\": \"123 Main St\", \"telefono\": \"555-555-5555\", \"identificacion\": \"123456789\", \"contrasena\": \"password\", \"estado\": true }"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateCliente_WithInvalidId_ReturnsNotFound() throws Exception {
        doThrow(new EntityNotFoundException()).when(clienteService).updateCliente(any());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"clienteId\": 1, \"nombre\": \"John Doe\", \"genero\": \"Masculino\", \"edad\": 30, \"direccion\": \"123 Main St\", \"telefono\": \"555-555-5555\", \"identificacion\": \"123456789\", \"contrasena\": \"password\", \"estado\": true }"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void createCliente_WithValidData_ReturnsCreated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"nombre\": \"John Doe\", \"genero\": \"Masculino\", \"edad\": 30, \"direccion\": \"123 Main St\", \"telefono\": \"555-555-5555\", \"identificacion\": \"123456789\", \"contrasena\": \"password\", \"estado\": true }"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}

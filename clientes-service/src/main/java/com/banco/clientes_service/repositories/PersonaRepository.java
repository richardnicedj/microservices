package com.banco.clientes_service.repositories;

import com.banco.clientes_service.model.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
}

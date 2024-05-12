package com.banco.clientes_service.repositories;

import com.banco.clientes_service.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT c FROM Cliente c WHERE c.status = 'ACTIVE'")
    List<Cliente> findAllActiveClients();

    @Query("SELECT c FROM Cliente c WHERE c.clienteId = :id AND c.status = 'ACTIVE'")
    Optional<Cliente> findActiveById(Long id);
}

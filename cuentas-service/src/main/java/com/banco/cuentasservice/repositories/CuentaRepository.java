package com.banco.cuentasservice.repositories;

import com.banco.cuentasservice.model.entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    @Query("SELECT c FROM Cuenta c WHERE c.status = 'ACTIVE'")
    List<Cuenta> findAllActiveCuentas();
    @Query("SELECT c FROM Cuenta c WHERE c.cuentaId = :id AND c.status = 'ACTIVE'")
    Optional<Cuenta> findActiveById(Long id);

    @Query("SELECT c FROM Cuenta c WHERE c.numeroCuenta = :numeroCuenta AND c.status = 'ACTIVE'")
    Optional<Cuenta> findActiveByNumeroCuenta(String numeroCuenta);

    @Query("SELECT c FROM Cuenta c WHERE c.cliente = :nombre AND c.status = 'ACTIVE'")
    List<Cuenta> findAllActiveCuentasByNombre(String nombre);
}

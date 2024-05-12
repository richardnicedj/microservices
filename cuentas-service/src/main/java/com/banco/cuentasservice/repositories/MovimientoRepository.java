package com.banco.cuentasservice.repositories;

import com.banco.cuentasservice.model.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    @Query("SELECT m FROM Movimiento m  WHERE m.status = 'ACTIVE'")
    List<Movimiento> findAllActiveMovimientos();
    @Query("SELECT m FROM Movimiento m WHERE m.status = 'ACTIVE' AND m.movimientoId = :id")
    Optional<Movimiento> findActiveByMovimientoId(Long id);
    @Query("SELECT m FROM Movimiento m WHERE m.status = 'ACTIVE' AND m.numeroCuenta = :numeroCuenta AND m.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY m.fecha DESC")
    List<Movimiento> findAlActiveMovimientosByCuentaIdAndFechaBetween(String numeroCuenta, LocalDate fechaInicio, LocalDate fechaFin);
}

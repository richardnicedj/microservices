package com.banco.cuentasservice.services;

import com.banco.cuentasservice.exceptions.SaldoNoDisponibleException;
import com.banco.cuentasservice.model.dtos.MovimientoDTO;
import com.banco.cuentasservice.model.dtos.ReporteEstadoCuentaDTO;
import com.banco.cuentasservice.model.entities.Cuenta;
import com.banco.cuentasservice.model.entities.Movimiento;
import com.banco.cuentasservice.repositories.MovimientoRepository;
import com.banco.cuentasservice.utils.Status;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;
    private final CuentaService cuentaService;
    public MovimientoDTO addMovimiento(MovimientoDTO movimientoDTO) {

        Double saldoMovimiento = getValue(movimientoDTO.getMovimiento());
        Double saldoDisponible = movimientoDTO.getSaldoInicial() + saldoMovimiento;
        if(!validateMovimiento(saldoDisponible)) {
            throw new SaldoNoDisponibleException("Saldo no disponible");
        }
        try {
            cuentaService.updateSaldoCuenta(movimientoDTO.getNumeroCuenta(), saldoDisponible);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al actualizar el saldo de la cuenta", e);
        }
        Movimiento movimiento = Movimiento.builder()
                .fecha(LocalDate.now())
                .numeroCuenta(movimientoDTO.getNumeroCuenta())
                .tipo(movimientoDTO.getTipo())
                .saldoInicial(movimientoDTO.getSaldoInicial())
                .estado(movimientoDTO.getEstado())
                .movimiento(movimientoDTO.getMovimiento())
                .status(Status.ACTIVE)
                .build();
        movimientoRepository.save(movimiento);
        log.info("Se agrego el movimiento {}", movimiento);
        return mapToMovimientoDTO(movimiento);
    }

    private boolean validateMovimiento(Double saldo) {
        return saldo >= 0;
    }

    public Iterable<MovimientoDTO> getAllMovimientos() {
        return movimientoRepository.findAllActiveMovimientos()
                .stream()
                .map(this::mapToMovimientoDTO)
                .toList();
    }

    public MovimientoDTO getMovimientoById(Long id) {
        Movimiento movimiento = movimientoRepository.findActiveByMovimientoId(id).orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrada con ID: " + id));
        log.info("Se encontro el movimiento {}", movimiento);
        return mapToMovimientoDTO(movimiento);
    }

    public MovimientoDTO updateMovimiento(MovimientoDTO movimientoDTO) {
        Movimiento movimiento = Movimiento.builder()
                .movimientoId(movimientoDTO.getMovimientoId())
                .fecha(movimientoDTO.getFecha())
                .numeroCuenta(movimientoDTO.getNumeroCuenta())
                .tipo(movimientoDTO.getTipo())
                .saldoInicial(movimientoDTO.getSaldoInicial())
                .estado(movimientoDTO.getEstado())
                .movimiento(movimientoDTO.getMovimiento())
                .status(Status.ACTIVE)
                .build();
        movimientoRepository.save(movimiento);
        log.info("Se actualizo el movimiento {}", movimiento);
        return mapToMovimientoDTO(movimiento);
    }

    public void deleteMovimiento(Long id) {
        Movimiento movimiento = movimientoRepository.findActiveByMovimientoId(id).orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrada con ID: " + id));
        movimiento.setStatus(Status.DELETED);
        movimientoRepository.save(movimiento);
        log.info("Se elimino el movimiento {}", movimiento);
    }

    private Double getValue(String movimiento) {
        double movimientoValue = 0;

        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher matcher = pattern.matcher(movimiento);

        if (matcher.find()) {
            String amountString = matcher.group();
            movimientoValue = Double.parseDouble(amountString);

            if (movimiento.startsWith("Retiro")) {
                movimientoValue = -movimientoValue;
            }
        }

        return movimientoValue;
    }


    private MovimientoDTO mapToMovimientoDTO(Movimiento movimiento) {
        return MovimientoDTO.builder()
                .movimientoId(movimiento.getMovimientoId())
                .fecha(movimiento.getFecha())
                .numeroCuenta(movimiento.getNumeroCuenta())
                .tipo(movimiento.getTipo())
                .saldoInicial(movimiento.getSaldoInicial())
                .estado(movimiento.getEstado())
                .movimiento(movimiento.getMovimiento())
                .build();
    }

    public List<ReporteEstadoCuentaDTO> obtenerMovimientosPorCuentaYRangoFecha(Cuenta cuenta, LocalDate fechaInicio, LocalDate fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return movimientoRepository.findAlActiveMovimientosByCuentaIdAndFechaBetween(cuenta.getNumeroCuenta(), fechaInicio, fechaFin)
                .stream()
                .map(movimiento -> {
            return ReporteEstadoCuentaDTO.builder()
                    .fecha(movimiento.getFecha().format(formatter))
                    .cliente(cuenta.getCliente())
                    .numeroCuenta(movimiento.getNumeroCuenta())
                    .tipo(movimiento.getTipo())
                    .saldoInicial(movimiento.getSaldoInicial())
                    .estado(movimiento.getEstado())
                    .movimiento(getValue(movimiento.getMovimiento()))
                    .saldoDisponible(movimiento.getSaldoInicial() + getValue(movimiento.getMovimiento()))
                    .build();
                }).toList();
    }
}

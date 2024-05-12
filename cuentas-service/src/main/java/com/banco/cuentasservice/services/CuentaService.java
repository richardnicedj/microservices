package com.banco.cuentasservice.services;

import com.banco.cuentasservice.model.dtos.CuentaDTO;
import com.banco.cuentasservice.model.entities.Cuenta;
import com.banco.cuentasservice.repositories.CuentaRepository;
import com.banco.cuentasservice.utils.Status;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaService {
    private final CuentaRepository cuentaRepository;

    public CuentaDTO addCuenta(CuentaDTO cuenta) {
        Cuenta newCuenta = Cuenta.builder()
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipo(cuenta.getTipo())
                .saldoInicial(cuenta.getSaldoInicial())
                .estado(cuenta.getEstado())
                .cliente(cuenta.getCliente())
                .status(Status.ACTIVE)
                .build();
        cuentaRepository.save(newCuenta);
        log.info("Se agrega la cuenta {}", newCuenta);
        return mapToCuentaDTO(newCuenta);
    }

    public CuentaDTO getCuentaById(Long id) {
        Cuenta cuenta = cuentaRepository.findActiveById(id).orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada con ID: " + id));
        log.info("Se encontro la cuenta {}", cuenta);
        return mapToCuentaDTO(cuenta);
    }

    public Iterable<CuentaDTO> getAllCuentas() {
        return cuentaRepository.findAllActiveCuentas()
                .stream()
                .map(this::mapToCuentaDTO)
                .toList();
    }

    public CuentaDTO updateCuenta(CuentaDTO cuentaRequest) {
        Cuenta cliente = cuentaRepository.findActiveById(cuentaRequest.getCuentaId()).orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada con ID: " + cuentaRequest.getCuentaId()));
        Cuenta savedCuenta = cuentaRepository.save(Cuenta.builder()
                .cuentaId(cliente.getCuentaId())
                .numeroCuenta(cuentaRequest.getNumeroCuenta())
                .tipo(cuentaRequest.getTipo())
                .saldoInicial(cuentaRequest.getSaldoInicial())
                .estado(cuentaRequest.getEstado())
                .cliente(cuentaRequest.getCliente())
                .status(Status.ACTIVE)
                .build());
        log.info("Se actualizo la cuenta {}", savedCuenta);
        return mapToCuentaDTO(savedCuenta);
    }

    public void deleteCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findActiveById(id).orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada con ID: " + id));
        cuenta.setStatus(Status.DELETED);
        cuentaRepository.save(cuenta);
        log.info("Se elimino la cuenta {}", cuenta);
    }

    public CuentaDTO getCuentaByNumeroCuenta(String numeroCuenta) {
        try {
            Cuenta cuenta = cuentaRepository.findActiveByNumeroCuenta(numeroCuenta).orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada con numero de cuenta: " + numeroCuenta));
            log.info("Se encontro la cuenta {}", cuenta);
            return mapToCuentaDTO(cuenta);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Cuenta no encontrada con numero de cuenta: " + numeroCuenta);
        }

    }

    private CuentaDTO mapToCuentaDTO(Cuenta cuenta) {
        return CuentaDTO.builder()
                .cuentaId(cuenta.getCuentaId())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipo(cuenta.getTipo())
                .saldoInicial(cuenta.getSaldoInicial())
                .estado(cuenta.getEstado())
                .cliente(cuenta.getCliente())
                .build();
    }
    public void updateSaldoCuenta(String numeroCuenta, Double saldoDisponible) {
        Cuenta cuenta = cuentaRepository.findActiveByNumeroCuenta(numeroCuenta).orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada con numero de cuenta: " + numeroCuenta));
        cuenta.setSaldoInicial(saldoDisponible);
        cuentaRepository.save(cuenta);
        log.info("Se actualizo el saldo de la cuenta {}", cuenta);
    }

    public List<Cuenta> obtenerCuentasPorCliente(String nombre) {

        return cuentaRepository.findAllActiveCuentasByNombre(nombre);
    }
}

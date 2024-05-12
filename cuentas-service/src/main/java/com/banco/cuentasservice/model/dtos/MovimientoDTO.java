package com.banco.cuentasservice.model.dtos;

import com.banco.cuentasservice.utils.Tipo;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimientoDTO {
    private Long movimientoId;
    private LocalDate fecha;
    @NonNull
    private String numeroCuenta;
    @NonNull
    private Tipo tipo;
    @NonNull
    private Double saldoInicial;
    @NonNull
    private Boolean estado;
    @NonNull
    private String movimiento;
}

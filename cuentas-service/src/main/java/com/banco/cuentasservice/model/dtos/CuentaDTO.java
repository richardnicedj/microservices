package com.banco.cuentasservice.model.dtos;

import com.banco.cuentasservice.utils.Tipo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuentaDTO {
    private Long cuentaId;
    private String numeroCuenta;
    private Tipo tipo;
    private Double saldoInicial;
    private String estado;
    private String cliente;
}

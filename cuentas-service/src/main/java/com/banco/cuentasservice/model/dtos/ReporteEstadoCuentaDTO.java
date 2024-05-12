package com.banco.cuentasservice.model.dtos;

import com.banco.cuentasservice.utils.Tipo;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReporteEstadoCuentaDTO {
    private String fecha;
    private String cliente;

    private String numeroCuenta;

    private Tipo tipo;

    private Double saldoInicial;

    private Boolean estado;

    private Double movimiento;
    private Double saldoDisponible;

    public String toJson() {
        return "{" +
                "\"Fecha\":\"" + fecha + "\"," +
                "\"Cliente\":\"" + cliente + "\"," +
                "\"Numero Cuenta\":\"" + numeroCuenta + "\"," +
                "\"Tipo\":\"" + tipo.getTipoString() + "\"," +
                "\"Saldo Inicial\":\"" + saldoInicial + "\"," +
                "\"Estado\":\"" + estado + "\"," +
                "\"Movimiento\":\"" + movimiento + "\"," +
                "\"Saldo Disponible\":\"" + saldoDisponible + "\"" +
                "}";
    }
}

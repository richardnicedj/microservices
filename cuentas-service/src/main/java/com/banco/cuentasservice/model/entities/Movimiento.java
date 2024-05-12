package com.banco.cuentasservice.model.entities;

import com.banco.cuentasservice.utils.Status;
import com.banco.cuentasservice.utils.Tipo;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "movimiento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movimientoId;
    private LocalDate fecha;
    private String numeroCuenta;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo;
    private Double saldoInicial;
    private Boolean estado;
    private String movimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Override
    public String toString() {
        return "Movimiento{" +
                "movimientoId=" + movimientoId +
                ", fecha=" + fecha +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", tipo=" + tipo +
                ", saldoInicial=" + saldoInicial +
                ", estado=" + estado +
                ", movimiento=" + movimiento +
                ", status=" + status +
                '}';
    }
}

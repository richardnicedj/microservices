package com.banco.cuentasservice.model.entities;

import com.banco.cuentasservice.utils.Status;
import com.banco.cuentasservice.utils.Tipo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cuenta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuentaId;
    @Column(unique = true)
    private String numeroCuenta;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo;
    private Double saldoInicial;
    private String estado;
    private String cliente;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Override
    public String toString() {
        return "Cuenta{" +
                "cuentaId=" + cuentaId +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", tipo=" + tipo +
                ", saldoInicial=" + saldoInicial +
                ", estado='" + estado + '\'' +
                ", cliente='" + cliente + '\'' +
                ", status=" + status +
                '}';
    }
}

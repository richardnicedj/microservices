package com.banco.clientes_service.model.entities;

import com.banco.clientes_service.utils.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;
    private String contrasena;
    private Boolean estado;
    @OneToOne
    @JoinColumn(name = "personaId")
    private Persona persona;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Override
    public String toString() {
        return "Cliente{" +
                "clienteId=" + clienteId +
                ", contrasena='" + contrasena + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}

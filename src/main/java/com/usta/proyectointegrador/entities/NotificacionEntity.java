package com.usta.proyectointegrador.entities;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity(name = "NOTIFICACION")
public class NotificacionEntity {

    @Version
    private Integer version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;
    private boolean leido = false;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private RolEntity rol;

    private Long idPostulacion;

}


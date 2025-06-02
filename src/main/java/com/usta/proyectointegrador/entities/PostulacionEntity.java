package com.usta.proyectointegrador.entities;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name="POSTULACIONES")
public class PostulacionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsersEntity usuario;

    @ManyToOne
    @JoinColumn(name="startup_id")
    private StartupEntity startup;

    @ManyToOne
    @JoinColumn(name = "convocatoria_id")
    private ConvocatoriaEntity convocatoria;

    private LocalDate fechaPostulacion;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "estado", length = 40)
    private String estado;

}

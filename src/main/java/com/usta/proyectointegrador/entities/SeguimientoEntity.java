package com.usta.proyectointegrador.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "SEGUIMIENTO")
public class SeguimientoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento")
    private Long idSeguimiento;

    @NotNull
    @Column(name = "comentario", length = 5000)
    private String comentario;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_seguimiento")
    private LocalDate fechaSeguimiento;

    // Relación con usuario (mentor)
    @NotNull
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UsersEntity mentor;

    // Relación con startup
    @NotNull
    @JoinColumn(name = "id_startup", referencedColumnName = "id_startup")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StartupEntity startup;
}

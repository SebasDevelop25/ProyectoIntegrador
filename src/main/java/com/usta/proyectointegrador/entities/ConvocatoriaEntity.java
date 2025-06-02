package com.usta.proyectointegrador.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "CONVOCATORIAS")
public class ConvocatoriaEntity  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_convocatoria")
    private Long id_Convocatoria;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "title_convocatoria", length = 40, nullable = false)
    private String titleConvocatoria;

    @NotNull
    @Size(min = 1, max = 10000)
    @Column(name = "descripcion", length = 10000)
    private String Descripcion;

    @NotNull
    @DateTimeFormat(pattern = "yyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @NotNull
    @DateTimeFormat(pattern = "yyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_fin")
    private Date fechaFin;

    @ManyToMany
    private List<StartupEntity> startups = new ArrayList<>();





}

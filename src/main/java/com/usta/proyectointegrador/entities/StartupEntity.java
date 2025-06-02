package com.usta.proyectointegrador.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "STARTUP")
public class StartupEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_startup")
    private Integer id_startup;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre_startup", length = 200, nullable = false)
    private String nombre_startup;

    @NotNull
    @Size(min = 1, max = 10000)
    @Column(name = "descripcion", length = 10000, nullable = false)
    private String descripción;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "sector", length = 200, nullable = false)
    private String sector;

    @NotNull
    @Size(min = 1, max = 1000000)
    @Column(name = "logo", length = 1000000, nullable = false)
    private String logo;



    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
    public String getSector() {
        return sector;
    }

    /*--------------*/
    @ManyToOne
    @JoinColumn(name = "id_convocatoria")
    private ConvocatoriaEntity convocatoria;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsersEntity usuario;

    public UsersEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsersEntity usuario) {
        this.usuario = usuario;
    }

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private RolEntity rol;




}

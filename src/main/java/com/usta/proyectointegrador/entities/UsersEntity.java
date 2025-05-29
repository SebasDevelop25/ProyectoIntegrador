package com.usta.proyectointegrador.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "USERS")
public class UsersEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id_usuario;

    @NotNull
    @Size(min = 20, max = 50)
    @Column(name = "nombre_usu", length = 40, nullable = false)
    private String nombre_usu;

    @NotNull
    @Size(min = 20, max = 50)
    @Column(name = "apellido_usu", length = 40, nullable = false)
    private String apellido_usu;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "email", unique = true,  length = 150, nullable = false)
    private String email;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "clave", length = 100, nullable = false)
    private String clave;

    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "ciudad", length = 50, nullable = false)
    private String ciudad;

    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "telefono", length = 15, nullable = false)
    private String telefono;

    @NotNull
    @Size(min = 1, max = 1000000)
    @Column(name = "foto", length = 1000000, nullable=false)
    private String foto;

    public void setFoto(String foto) {
        this.foto = foto;
    }
    public String getFoto() {
        return foto;
    }

    @NotNull
    @JoinColumn(name = "id_Rol", referencedColumnName = "id_Rol")
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RolEntity idRol;

}

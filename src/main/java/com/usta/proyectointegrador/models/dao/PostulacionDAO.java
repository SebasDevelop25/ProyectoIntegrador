package com.usta.proyectointegrador.models.dao;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;
import com.usta.proyectointegrador.entities.PostulacionEntity;
import com.usta.proyectointegrador.entities.StartupEntity;
import com.usta.proyectointegrador.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostulacionDAO extends JpaRepository<PostulacionEntity, Long> {
    @Query("SELECT p FROM PostulacionEntity p WHERE p.usuario = :usuario")
    List<PostulacionEntity> findByUsuario(@Param("usuario")UsersEntity users);

    @Query("SELECT p FROM PostulacionEntity p WHERE p.convocatoria = :convocatoria")
    List<PostulacionEntity> findByConvocatoria(@Param("convocatoria") ConvocatoriaEntity convocatoria);

    @Query("SELECT p FROM PostulacionEntity p WHERE p.convocatoria = :convocatoria")
    PostulacionEntity findByConv(@Param("convocatoria") ConvocatoriaEntity convocatoria);

    @Query("SELECT p FROM PostulacionEntity p WHERE p.startup.nombre_startup = :nombre")
    PostulacionEntity findByStartupNombreExtraido(@Param("nombre") String nombre);

    @Query("""
           SELECT p
           FROM PostulacionEntity p
           WHERE p.usuario.idUsuario = :userId
           ORDER BY p.fechaPostulacion DESC
           """)
    List<PostulacionEntity> findByUsuarioId(@Param("userId") Long userId);



}

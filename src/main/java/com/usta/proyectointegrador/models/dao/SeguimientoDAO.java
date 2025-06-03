package com.usta.proyectointegrador.models.dao;

import com.usta.proyectointegrador.entities.SeguimientoEntity;
import com.usta.proyectointegrador.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguimientoDAO extends JpaRepository<SeguimientoEntity, Long> {
    @Query("""
           SELECT s
           FROM SeguimientoEntity s
           WHERE s.mentor.idUsuario = :usuarioId
           """)
    List<SeguimientoEntity> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("""
       SELECT s
       FROM SeguimientoEntity s
       WHERE s.startup.id_startup = :startupId
       ORDER BY s.fechaSeguimiento DESC
       """)
    List<SeguimientoEntity> findByStartupId(@Param("startupId") Integer startupId);
}

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
           WHERE s.mentor.id_usuario = :usuarioId
           """)
    List<SeguimientoEntity> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}

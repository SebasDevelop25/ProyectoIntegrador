package com.usta.proyectointegrador.models.dao;

import com.usta.proyectointegrador.entities.RolEntity;
import com.usta.proyectointegrador.entities.SeguimientoEntity;
import com.usta.proyectointegrador.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Query("""
            SELECT s
            FROM SeguimientoEntity s
            WHERE s.startup.id_startup = :startupId
            """)
    List<SeguimientoEntity> findByStartupId(@Param("startupId") Long startupId);

    @Query("""
       SELECT s
       FROM SeguimientoEntity s
       WHERE s.startup.usuario.idUsuario = :idEmprendedor
       ORDER BY s.fechaSeguimiento DESC
       """)
    List<SeguimientoEntity> findByStartupUsuarioId(@Param("idEmprendedor") Long idEmprendedor);

    @Transactional
    @Query("SELECT s FROM SeguimientoEntity  s WHERE s.idSeguimiento =:rolId")
    SeguimientoEntity findByIdSE(Integer rolId);

}

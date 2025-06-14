package com.usta.proyectointegrador.models.dao;

import com.usta.proyectointegrador.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionDAO extends JpaRepository<TransactionEntity, Long> {

    @Query("""
            SELECT t
            FROM TransactionEntity t
            WHERE t.usuario.idUsuario = :usuarioId
            """)
    List<TransactionEntity> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("""
            SELECT t
            FROM TransactionEntity t
            WHERE t.startup.id_startup = :startupId
    """)
    List<TransactionEntity> findByStartupId(@Param("startupId") Long startupId);

    @Query("SELECT t " +
            "FROM TransactionEntity t " +
            "WHERE t.startup.usuario.idUsuario = :idEmprendedor")
    List<TransactionEntity> findByStartupUsuarioId(@Param("idEmprendedor") Long idEmprendedor);

}

package com.usta.proyectointegrador.models.dao;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;
import com.usta.proyectointegrador.entities.StartupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StartupDAO extends JpaRepository<StartupEntity, Long> {

    @Query("SELECT s FROM StartupEntity s WHERE s.convocatoria = :convocatoria")
    List<StartupEntity> findByConvocatoria(@Param("convocatoria") ConvocatoriaEntity convocatoria);

    @Query("""
    SELECT s
    FROM StartupEntity s
    WHERE NOT EXISTS (
        SELECT 1 FROM SeguimientoEntity seg
        WHERE seg.startup.id_startup = s.id_startup AND seg.mentor.id_usuario = :mentorId
    )
""")
    List<StartupEntity> findStartupsSinSeguimientoPorMentor(@Param("mentorId") Long mentorId);


}

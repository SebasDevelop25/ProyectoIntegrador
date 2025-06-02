package com.usta.proyectointegrador.models.dao;

import com.usta.proyectointegrador.entities.NotificacionEntity;
import com.usta.proyectointegrador.entities.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionDAO extends JpaRepository<NotificacionEntity, Long> {
    List<NotificacionEntity> findByRolAndLeidoFalse(RolEntity rol);
}

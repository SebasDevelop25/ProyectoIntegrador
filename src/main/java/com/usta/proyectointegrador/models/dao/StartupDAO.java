package com.usta.proyectointegrador.models.dao;

import com.usta.proyectointegrador.entities.StartupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StartupDAO extends JpaRepository<StartupEntity, Long> {
}

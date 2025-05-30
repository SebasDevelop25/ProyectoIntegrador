package com.usta.proyectointegrador.models.dao;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ConvocatorioDAO extends JpaRepository<ConvocatoriaEntity, Long> {


}

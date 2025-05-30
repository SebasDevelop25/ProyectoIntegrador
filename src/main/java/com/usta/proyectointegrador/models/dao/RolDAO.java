package com.usta.proyectointegrador.models.dao;

import com.usta.proyectointegrador.entities.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RolDAO extends JpaRepository<RolEntity, Long> {
    @Transactional
    @Query("SELECT r FROM RolEntity r WHERE r.Rol =:rol")
    RolEntity findByNombre(String rol);

    @Transactional
    @Query("SELECT r FROM RolEntity  r WHERE r.idRol =:rolId")
    RolEntity findById(Integer rolId);
}

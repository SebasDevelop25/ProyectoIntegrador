package com.usta.proyectointegrador.models.dao;

import com.usta.proyectointegrador.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersDAO extends JpaRepository<UsersEntity, Long> {


    @Query("SELECT u FROM UsersEntity u WHERE u.idRol.idRol = 2")
    List<UsersEntity> findByRol();
}

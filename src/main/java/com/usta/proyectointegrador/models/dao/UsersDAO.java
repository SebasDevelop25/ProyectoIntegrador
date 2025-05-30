package com.usta.proyectointegrador.models.dao;

import com.usta.proyectointegrador.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersDAO extends JpaRepository<UsersEntity, Long> {

    UsersEntity findByEmail(String email);

    @Query("SELECT u FROM UsersEntity u WHERE u.idRol.idRol = 3")
    List<UsersEntity> findByRol();
}

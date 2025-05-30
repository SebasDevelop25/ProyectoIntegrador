package com.usta.proyectointegrador.models.dao;

import com.usta.proyectointegrador.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDAO extends JpaRepository<UsersEntity, Long> {

    UsersEntity findByEmail(String email);

}

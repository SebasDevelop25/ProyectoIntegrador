package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.UsersEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersServices {
    public List<UsersEntity> findAll();

    public void save(UsersEntity usuario);

    public UsersEntity findById(Long id);

    public void deleteById(Long id);

    public UsersEntity actualizarUsuario(UsersEntity usuario);

    public UsersEntity findByEmail(String email);

    public List<UsersEntity> findByRol(Long id);

}

package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.UsersEntity;
import com.usta.proyectointegrador.models.dao.UsersDAO;

import java.util.List;

public interface UsersServices {
    public List<UsersEntity> findAll();

    public void save(UsersEntity user);

    public UsersEntity findById(Integer id);

    public void deleteById(Integer id);

    public UsersEntity actualizarUsu(UsersEntity user);

    public List<UsersEntity> findByRol(Long id);


}

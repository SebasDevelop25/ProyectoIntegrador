package com.usta.proyectointegrador.models.services;
import com.usta.proyectointegrador.entities.UsersEntity;
import com.usta.proyectointegrador.models.dao.UsersDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServicesImplement implements UsersServices {

    @Autowired
    UsersDAO usersDAO;

    @Override
    public List<UsersEntity> findAll() {
        return (List<UsersEntity>) usersDAO.findAll();
    }
    @Override
    public void save(UsersEntity user) {
        usersDAO.save(user);
    }

    @Override
    @Transactional
    public UsersEntity findById(Long id) {
        return usersDAO.findById(id.longValue()).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        usersDAO.deleteById(id.longValue());

    }

    @Override
    public UsersEntity actualizarUsu(UsersEntity user) {
        return usersDAO.save(user);
    }

    @Override
    @Transactional
    public List<UsersEntity> findByRol(Long id) {
        return usersDAO.findByRol();
    }
}

package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.UsersEntity;
import com.usta.proyectointegrador.models.dao.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsersServicesImplement implements UsersServices {

    @Autowired
    private UsersDAO usuarioDAO;

    @Override
    @Transactional(readOnly = true)
    public List<UsersEntity> findAll() {
        return usuarioDAO.findAll();
    }

    @Override
    @Transactional
    public void save(UsersEntity usuario) {
        usuarioDAO.save(usuario);
    }

    @Override
    public UsersEntity findById(Long id) {
        return usuarioDAO.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        usuarioDAO.deleteById(id);

    }

    @Override
    public UsersEntity actualizarUsuario(UsersEntity usuario) {
        return null;
    }

    @Override
    public UsersEntity findByEmail(String email) {
        return usuarioDAO.findByEmail(email);
    }

    @Override
    @jakarta.transaction.Transactional
    public List<UsersEntity> findByRol(Long id) {
        return usuarioDAO.findByRol();
    }
}

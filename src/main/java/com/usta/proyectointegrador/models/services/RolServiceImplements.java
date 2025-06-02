package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.RolEntity;
import com.usta.proyectointegrador.models.dao.RolDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RolServiceImplements implements RolService  {

    @Autowired
    private RolDAO rolDAO;
    @Override
    public List<RolEntity> findAll() {
        {return(List<RolEntity>) rolDAO.findAll();}
    }

    @Override
    public RolEntity findById(Long id) {
        return  rolDAO.findById(id).orElse(null);
    }

    @Override
    public void save(RolEntity rol) {
        rolDAO.save(rol);

    }

    @Override
    public void deleteById(Long rol) {
        rolDAO.deleteById(rol);

    }
}

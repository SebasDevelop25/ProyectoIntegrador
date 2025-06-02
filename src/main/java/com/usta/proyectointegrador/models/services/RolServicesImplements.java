package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.RolEntity;
import com.usta.proyectointegrador.models.dao.RolDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServicesImplements implements RolServices {

    @Autowired
    private RolDAO rolDAO;

    @Override
    public List<RolEntity> findAll() {
        return List.of();
    }

    @Override
    public void save(RolEntity rol) {

    }

    @Override
    public RolEntity findById(Integer id) {
        return rolDAO.findById(id);
    }

    @Override
    public RolEntity findByIdS(Long id) {
        return rolDAO.findByIdV(id);
    }

    @Override
    public RolEntity findByNombre(String rol) {
        return rolDAO.findByNombre(rol);
    }
}

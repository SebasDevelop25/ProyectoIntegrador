package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.StartupEntity;
import com.usta.proyectointegrador.models.dao.StartupDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StartupServicesImplement implements StartupServices {

    @Autowired
    StartupDAO startupDAO;
    @Override
    public List<StartupEntity> findAll() {
        return (List<StartupEntity>) startupDAO.findAll();
    }

    @Override
    public void save(StartupEntity startup) {
        startupDAO.save(startup);

    }

    @Override
    public StartupEntity findById(Long id) {
        return startupDAO.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        startupDAO.deleteById(id);

    }

    @Override
    public StartupEntity actualizarStar(StartupEntity startup) {
        return startupDAO.save(startup);
    }
}

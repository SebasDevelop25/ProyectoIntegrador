package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;
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
    public StartupEntity findById(Integer id) {

        return startupDAO.findById(id.longValue()).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        startupDAO.deleteById(id.longValue());

    }

    @Override
    public StartupEntity actualizarStar(StartupEntity startup) {

        return startupDAO.save(startup);
    }

    @Override
    public List<StartupEntity> findByConvocatoria(ConvocatoriaEntity convocatoria) {
        return startupDAO.findByConvocatoria(convocatoria);
    }

    @Override
    public List<StartupEntity> findByUsuario(Long id) {
        return startupDAO.findByUsuario(id);
    }

}

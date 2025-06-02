package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;
import com.usta.proyectointegrador.entities.StartupEntity;
import com.usta.proyectointegrador.models.dao.ConvocatorioDAO;
import com.usta.proyectointegrador.models.dao.StartupDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvocatoriaServicesImplement implements ConvocatoriaServices {


    @Autowired
    private ConvocatorioDAO convocatorioDAO;
    @Autowired
    private StartupDAO startupDAO;

    @Override
    @Transactional
    public List<ConvocatoriaEntity> findAll() {
        return (List<ConvocatoriaEntity>) convocatorioDAO.findAll();
    }

    @Override
    public ConvocatoriaEntity findById(Long id) {
        {
            return convocatorioDAO.findById(id).orElse(null);
        }
    }

    @Override
    public ConvocatoriaEntity findByConvo(ConvocatoriaEntity convocatoria) {
        return null;
    }

    @Override
    public void save(ConvocatoriaEntity convocatoria) {
        convocatorioDAO.save(convocatoria);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        convocatorioDAO.deleteById(id);

    }

    @Override
    public ConvocatoriaEntity actualizar(ConvocatoriaEntity convocatoria) {
        return convocatorioDAO.save(convocatoria);
    }

    @Override
    public void registrarStartupEnConvocatoria(StartupEntity startup, ConvocatoriaEntity convocatoria) {
        startup.setConvocatoria(convocatoria);
        startupDAO.save(startup);
    }

}

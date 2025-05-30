package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;
import com.usta.proyectointegrador.models.dao.ConvocatorioDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvocatoriaServicesImplement implements ConvocatoriaServices {


    @Autowired
    private ConvocatorioDAO convocatorioDAO;

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
}

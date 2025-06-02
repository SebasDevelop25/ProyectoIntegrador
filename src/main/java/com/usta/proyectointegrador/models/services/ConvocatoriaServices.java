package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;
import com.usta.proyectointegrador.entities.StartupEntity;

import java.util.List;

public interface ConvocatoriaServices {

    public List<ConvocatoriaEntity> findAll();

    public ConvocatoriaEntity findById(Long id);

    public ConvocatoriaEntity findByConvo(ConvocatoriaEntity convocatoria);

    public void save(ConvocatoriaEntity convocatoria);

    public void deleteById(Long id);

    public ConvocatoriaEntity actualizar(ConvocatoriaEntity convocatoria);

    public void registrarStartupEnConvocatoria(StartupEntity startup, ConvocatoriaEntity convocatoria);
}

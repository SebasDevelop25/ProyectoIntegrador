package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;

import java.util.List;

public interface ConvocatoriaServices {

    public List<ConvocatoriaEntity> findAll();

    public ConvocatoriaEntity findById(Long id);

    public void save(ConvocatoriaEntity convocatoria);

    public void deleteById(Long id);

    public ConvocatoriaEntity actualizar(ConvocatoriaEntity convocatoria);
}

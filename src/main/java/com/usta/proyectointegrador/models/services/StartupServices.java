package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;
import com.usta.proyectointegrador.entities.StartupEntity;

import java.util.List;

public interface StartupServices {
    public List<StartupEntity> findAll();

    public void save(StartupEntity startup);

    public StartupEntity findById(Integer id);

    public void deleteById(Integer id);

    public StartupEntity actualizarStar(StartupEntity startup);

    List<StartupEntity> findByConvocatoria(ConvocatoriaEntity convocatoria);

    List<StartupEntity> findByUsuario(Long id);

}

package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.StartupEntity;

import java.util.List;

public interface StartupServices {
    public List<StartupEntity> findAll();

    public void save(StartupEntity startup);

    public StartupEntity findById(Long id);

    public void deleteById(Long id);

    public StartupEntity actualizarStar(StartupEntity startup);

}

package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.RolEntity;

import java.util.List;

public interface RolService {

    public List<RolEntity> findAll();

    public RolEntity findById(Long id);

    public void save(RolEntity rol);

    public void deleteById(Long rol);



}

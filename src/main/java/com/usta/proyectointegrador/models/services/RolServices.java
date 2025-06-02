package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.RolEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RolServices {
    public List<RolEntity> findAll();

    public void save(RolEntity rol);

    public RolEntity findById(Integer id);

    public RolEntity findByIdS(Long id);

    public RolEntity findByNombre(String rol);
}

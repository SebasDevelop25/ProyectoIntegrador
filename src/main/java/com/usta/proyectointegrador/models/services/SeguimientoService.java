package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.RolEntity;
import com.usta.proyectointegrador.entities.SeguimientoEntity;

import java.util.List;

public interface SeguimientoService {

    public List<SeguimientoEntity> findAll();

    public RolEntity findById(Integer id);

    public List<SeguimientoEntity> findByUsuario(Long idUsuario);

    List<SeguimientoEntity> findByStartupUsuarioId(Long idEmprendedor);

    public List<SeguimientoEntity> findByIdStartup(Integer idStartup);

    public void save(SeguimientoEntity seguimiento);

    public void deleteById(Long seguimiento);

    public SeguimientoEntity findByIds(Integer id);


}

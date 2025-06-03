package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.RolEntity;
import com.usta.proyectointegrador.entities.SeguimientoEntity;
import com.usta.proyectointegrador.models.dao.SeguimientoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeguimientoServiceImplements implements SeguimientoService{

    @Autowired
    private SeguimientoDAO seguimientoDAO;

    @Override
    public List<SeguimientoEntity> findAll() {
        return List.of();
    }

    @Override
    public RolEntity findById(Integer id) {
        return null;
    }

    @Override
    public List<SeguimientoEntity> findByUsuario(Long idUsuario) {
        return seguimientoDAO.findByUsuarioId(idUsuario);
    }

    @Override
    public List<SeguimientoEntity> findByIdStartup(Integer idStartup) {
        return seguimientoDAO.findByStartupId(idStartup);
    }

    @Override
    public void save(SeguimientoEntity seguimiento) {
        seguimientoDAO.save(seguimiento);
    }

    @Override
    public void deleteById(Long seguimiento) {

    }
}

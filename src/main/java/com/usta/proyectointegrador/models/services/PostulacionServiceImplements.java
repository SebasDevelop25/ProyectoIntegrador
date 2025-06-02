package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;
import com.usta.proyectointegrador.entities.PostulacionEntity;
import com.usta.proyectointegrador.entities.UsersEntity;
import com.usta.proyectointegrador.models.dao.PostulacionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostulacionServiceImplements implements PostulacionServices {

    @Autowired
    PostulacionDAO postulacionDAO;

    @Override
    public List<PostulacionEntity> findall() {
        return (List<PostulacionEntity>) postulacionDAO.findAll();
    }

    @Override
    public PostulacionEntity findById(Long id) {
        return postulacionDAO.findById(id).orElse(null);
    }

    @Override
    public void save(PostulacionEntity postulacion) {
        postulacionDAO.save(postulacion);

    }

    @Override
    public void deleteById(Long id) {
        postulacionDAO.deleteById(id);

    }


    @Override
    public List<PostulacionEntity> findByConvocatoria(ConvocatoriaEntity convocatoria) {
        return (List<PostulacionEntity>) postulacionDAO.findByConvocatoria(convocatoria);
    }

    @Override
    public PostulacionEntity findByConv(ConvocatoriaEntity convocatoria) {
        return postulacionDAO.findByConv(convocatoria);
    }

    @Override
    public List<PostulacionEntity> findByUsuario(Long users) {
        return postulacionDAO.findByUsuarioId(users);
    }


}

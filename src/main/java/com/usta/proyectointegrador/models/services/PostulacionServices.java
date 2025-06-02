package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.ConvocatoriaEntity;
import com.usta.proyectointegrador.entities.PostulacionEntity;
import com.usta.proyectointegrador.entities.UsersEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface PostulacionServices {

    public List<PostulacionEntity> findall();

    public PostulacionEntity findById(Long id);

    public void save(PostulacionEntity postulacion);

    public void deleteById(Long id);


    List<PostulacionEntity> findByConvocatoria(ConvocatoriaEntity convocatoria);

    PostulacionEntity findByConv(ConvocatoriaEntity convocatoria);

    public List<PostulacionEntity> findByUsuario(Long users);
}

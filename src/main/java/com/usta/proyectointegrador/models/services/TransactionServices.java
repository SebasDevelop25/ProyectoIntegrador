package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.TransactionEntity;

import java.util.List;

public interface TransactionServices {

    List<TransactionEntity> findByUsuarioIdUsuario(Long idUsuario);

    TransactionEntity findById(Integer id);

    List<TransactionEntity> findByIdStartup(Integer idStartup);

    public void save(TransactionEntity transaction);

    public void deleteById(Integer idTransaction);

    public List<TransactionEntity> findAll();

}

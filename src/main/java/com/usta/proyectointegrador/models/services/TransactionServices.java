package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.TransactionEntity;

import java.util.List;

public interface TransactionServices {

    List<TransactionEntity> findByUsuarioIdUsuario(Integer idUsuario);
}

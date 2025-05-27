package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.TransactionEntity;
import com.usta.proyectointegrador.models.dao.TransactionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TransactionServicesImplement implements TransactionServices {
    @Autowired
    TransactionDAO transactionDAO;
    @Override
    public List<TransactionEntity> findByUsuarioIdUsuario(Integer idUsuario) {
        return transactionDAO.findByUsuarioId(idUsuario);
    }
}

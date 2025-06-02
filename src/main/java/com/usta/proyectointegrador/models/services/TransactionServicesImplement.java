package com.usta.proyectointegrador.models.services;

import com.usta.proyectointegrador.entities.TransactionEntity;
import com.usta.proyectointegrador.models.dao.TransactionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServicesImplement implements TransactionServices {
    @Autowired
    TransactionDAO transactionDAO;

    @Override
    public List<TransactionEntity> findByUsuarioIdUsuario(Long idUsuario) {
        return transactionDAO.findByUsuarioId(idUsuario);
    }

    @Override
    public TransactionEntity findById(Integer id) {
        return transactionDAO.findById(id.longValue()).orElse(null);
    }

    @Override
    public List<TransactionEntity> findByIdStartup(Integer idStartup) {
        return transactionDAO.findByStartupId(idStartup.longValue());
    }

    @Override
    @Transactional
    public void save(TransactionEntity transaction) {
        transactionDAO.save(transaction);
    }

    @Override
    public void deleteById(Integer idTransaction) {
        transactionDAO.deleteById(idTransaction.longValue());

    }
}

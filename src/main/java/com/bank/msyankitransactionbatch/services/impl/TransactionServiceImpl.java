package com.bank.msyankitransactionbatch.services.impl;

import com.bank.msyankitransactionbatch.models.dao.TransactionDao;
import com.bank.msyankitransactionbatch.models.documents.Transaction;
import com.bank.msyankitransactionbatch.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao dao;

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Override
    public Optional<Transaction> create(Transaction transaction) {
        transaction.setDateRegister(LocalDateTime.now());
        return Optional.of(dao.save(transaction));
    }

    @Override
    public Optional<Transaction> update(Transaction transaction) {
        transaction.setDateUpdate(LocalDateTime.now());
        return Optional.of(dao.save(transaction));
    }

}

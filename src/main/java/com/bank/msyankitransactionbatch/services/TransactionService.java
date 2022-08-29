package com.bank.msyankitransactionbatch.services;

import com.bank.msyankitransactionbatch.models.documents.Transaction;

import java.util.Optional;

public interface TransactionService {
    Optional<Transaction> create(Transaction transaction);

    Optional<Transaction> update(Transaction transaction);

}

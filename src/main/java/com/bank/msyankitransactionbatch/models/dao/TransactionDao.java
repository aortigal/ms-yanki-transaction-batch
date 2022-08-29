package com.bank.msyankitransactionbatch.models.dao;

import com.bank.msyankitransactionbatch.models.documents.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionDao extends MongoRepository<Transaction, String> {
}

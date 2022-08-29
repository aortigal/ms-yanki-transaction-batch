package com.bank.msyankitransactionbatch.services;

import com.bank.msyankitransactionbatch.models.utils.DataEvent;
import com.bank.msyankitransactionbatch.models.documents.Transaction;

public interface ProcessTransactionService {

    void process(DataEvent<Transaction> dataEvent);
}

package com.bank.msyankitransactionbatch.services;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProcessTransactionService {

    void process(String message) throws JsonProcessingException;
}

package com.bank.msyankitransactionbatch.services.impl;

import com.bank.msyankitransactionbatch.constants.Constant;
import com.bank.msyankitransactionbatch.models.documents.Transaction;
import com.bank.msyankitransactionbatch.models.utils.DataEvent;
import com.bank.msyankitransactionbatch.services.ProcessTransactionService;
import com.bank.msyankitransactionbatch.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProcessTransactionServiceImpl implements ProcessTransactionService {

    @Autowired
    private TransactionService transactionService;

    private static final Logger log = LoggerFactory.getLogger(ProcessTransactionServiceImpl.class);

    @Override
    public void process(DataEvent<Transaction> dataEvent) {
        log.info("[INI] Process {}", dataEvent.getId());
        if(dataEvent.getProcess().equals(Constant.PROCESS_TRANSACTION_CREATE)){
            log.info("save Transaction");
            transactionService.create(dataEvent.getData());
        }else if(dataEvent.getProcess().equals(Constant.PROCESS_TRANSACTION_UPDATE)){
            log.info("update Transaction");
            transactionService.update(dataEvent.getData());
        }else{
            log.info("Procces Invalid {}", dataEvent.getProcess());
        }

        log.info("[END] Process {}", dataEvent.getId());
    }

}

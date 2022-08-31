package com.bank.msyankitransactionbatch.services.impl;

import com.bank.msyankitransactionbatch.constants.Constant;
import com.bank.msyankitransactionbatch.models.documents.Transaction;
import com.bank.msyankitransactionbatch.models.documents.TransactionTransfer;
import com.bank.msyankitransactionbatch.models.utils.DataEvent;
import com.bank.msyankitransactionbatch.producer.KafkaProducer;
import com.bank.msyankitransactionbatch.services.ProcessTransactionService;
import com.bank.msyankitransactionbatch.services.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class ProcessTransactionServiceImpl implements ProcessTransactionService {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private KafkaProducer kafkaProducer;

    private static final Logger log = LoggerFactory.getLogger(ProcessTransactionServiceImpl.class);

    @Override
    public void process(String message) throws JsonProcessingException{

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DataEvent<?> dataEvent = objectMapper.readValue(message, new TypeReference<DataEvent<?>>() {});

        log.info("[INI] Process {}", dataEvent.getId());

        switch (dataEvent.getProcess()){
            case Constant.PROCESS_TRANSACTION_CREATE:
                log.info("save Transaction");
                Transaction t =objectMapper.readValue(message, new TypeReference<DataEvent<Transaction>>() {})
                        .getData();

                Optional<Transaction> transaction= transactionService.create(t);
                if (transaction.isPresent()){
                    DataEvent<Transaction> transactionDataEvent = new DataEvent<>();
                    transactionDataEvent.setId(dataEvent.getId());
                    transactionDataEvent.setProcess(Constant.PROCESS_WALLET_TRANSACTION);
                    transactionDataEvent.setDateEvent(LocalDateTime.now());
                    transactionDataEvent.setData(transaction.get());
                    kafkaProducer.sendMessage(transactionDataEvent);
                }
                break;
            case Constant.PROCESS_TRANSACTION_UPDATE:
                log.info("update Transaction");
                Transaction tupdate =objectMapper.readValue(message, new TypeReference<DataEvent<Transaction>>() {})
                        .getData();
                transactionService.update(tupdate);
                break;
            case Constant.PROCESS_BOOTCOIN_TRANSFER_YANKI_TRANSACTION:
                log.info("bootcoin Transfer Transaction");
                TransactionTransfer transactionTransfer = objectMapper.readValue(message, new TypeReference<DataEvent<TransactionTransfer>>() {})
                        .getData();
                Transaction transactionYanki= new Transaction();
                transactionYanki.setAmount(transactionTransfer.getAmount());
                transactionYanki.setSenderName(transactionTransfer.getSenderName());
                transactionYanki.setSenderPhone(transactionTransfer.getSenderAccount());

                Optional<Transaction> transactionOptional= transactionService.create(transactionYanki);
                if (transactionOptional.isPresent()){
                    DataEvent<Transaction> transactionDataEvent = new DataEvent<>();
                    transactionDataEvent.setId(dataEvent.getId());
                    transactionDataEvent.setProcess(Constant.PROCESS_WALLET_TRANSACTION_BOOTCOIN);
                    transactionDataEvent.setDateEvent(LocalDateTime.now());
                    transactionDataEvent.setData(transactionOptional.get());
                    kafkaProducer.sendMessage(transactionDataEvent);
                }
                break;
            default:
                log.info("Procces Invalid {}", dataEvent.getProcess());
                break;
        }
        log.info("[END] Process {}", dataEvent.getId());
    }

}

package com.bank.msyankitransactionbatch.consumer;

import com.bank.msyankitransactionbatch.models.documents.Transaction;
import com.bank.msyankitransactionbatch.models.utils.DataEvent;
import com.bank.msyankitransactionbatch.services.ProcessTransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private ProcessTransactionService processTransactionService;
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${kafka.topic.transaction}")
    public void consume(String message) {
        log.info("[INI] Consume");
        log.info("Consuming Message {}", message);
        try{
            processTransactionService.process(message);
        }catch (JsonProcessingException e){
            log.error("JsonProcessingException {}", e.getMessage());
        }
        log.info("[END] Consume");
    }

}
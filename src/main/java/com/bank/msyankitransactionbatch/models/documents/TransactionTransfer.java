package com.bank.msyankitransactionbatch.models.documents;

import com.bank.msyankitransactionbatch.models.enums.PayMode;
import com.bank.msyankitransactionbatch.models.utils.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionTransfer {

    @Id
    private String id;

    private String ticket;

    private String senderAccount;

    private String senderName;

    private String recipientAccount;

    private String recipientName;

    private Double rateAmount;

    private BigDecimal amount;

    private PayMode payMode;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStamp;

    private Status status;
}

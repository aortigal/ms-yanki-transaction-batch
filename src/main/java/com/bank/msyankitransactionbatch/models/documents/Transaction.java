package com.bank.msyankitransactionbatch.models.documents;

import com.bank.msyankitransactionbatch.models.utils.Audit;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "transactions")
public class Transaction extends Audit {

    @Id
    private String id;

    private String senderName;

    private String senderPhone;

    private float amount;

}

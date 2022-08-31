package com.bank.msyankitransactionbatch.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {

    RECEIVED(1,"RECEIVED"),
    CREATE(2,"CREATE"),
    INPROCESS(3,"IN-PROCESS"),
    SUCCESS(4,"SUCCESS"),
    ERROR(5,"ERROR");

    private final int code;
    private final String description;

}

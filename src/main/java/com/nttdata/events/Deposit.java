package com.nttdata.events;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Deposit {

    private Long phoneNumber;
    private BigDecimal amount;
    private int operationType;
}

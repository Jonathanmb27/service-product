package com.nttdata.client.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    private Long cardNumber;
    private BigDecimal debitAmount;
    /**
     * 1 deposito
     * 2 retiro
     *
     * 3 pago tc
     * 4 cargo consumo
     * */
    private int typeTransaction;

}

package com.nttdata.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {


    private String userId;

    /**
     * 1 Personal
     * 2 Empresa
     * */
    private int clientType;
    /**
     * 1 activo
     * 2 pasivo
     * */
    private int productType;
    /**p
     * 1: Ahorro
     * 2: Cuenta corriente
     * 3: Plazo fijo
     *
     * TC
     * 4: Personal
     * 5: Empresarial
     * 6: Tarjeta de Crédito personal o empresarial
     * */
    private int accountType;
    private Long cardNumber;
    private BigDecimal amount;
    private int maxNumberTransactions;
    private int minAmountMonth;
    private BigDecimal creditLimit;


}

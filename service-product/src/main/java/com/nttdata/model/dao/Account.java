package com.nttdata.model.dao;

import com.nttdata.model.dao.util.AccountType;
import com.nttdata.model.dao.util.ProdcutType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Account extends AbstractDocument{



    private AccountType accountType;
    private ProdcutType prodcutType;
    private Long cardNumber;
    private BigDecimal amount;

    /**
     * funcionalidad agregada
     * para el proyecto de la semana ||
     * numero maximo de transacciones por mes
     * */
    private int maxNumberTransactions=3;

    /**
     * funcionalidad agregada
     * para el proyecto de la semana ||
     * */
    private int minAmountMonth;

    /**
     * Uso para las tarjetas de credito
     * */
    private BigDecimal creditLimit;


    /**
     * Transaccion actual
     * */
    private int currentTransaction;

    /**
     * funcionalidad agregada
     * para el proyecto de la semana ||
     * cobro de comisiones
     * */
    private int commission=12;
}

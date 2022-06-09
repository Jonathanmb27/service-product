package com.nttdata.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductRequest {



    /**
     * id del cleinte que va crear la cuenta
     * */
    private String customerId;

    /**
     * 1: persona
     * 2: empresa
     * */
    private int customerType;

    /**
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

    /**
    * Monto del deposito
    * */
    private BigDecimal amount;

    private String cardNumber;

    /**
     * 1: pasivo
     * 2: activo
     * */
        private int productType;
}

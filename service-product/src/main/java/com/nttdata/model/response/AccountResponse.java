package com.nttdata.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {


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
    private String accountType;

    private String cardNumber;
    private BigDecimal amount;
}

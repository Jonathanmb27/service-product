package com.nttdata.model.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class YanquiDeposiExist implements Serializable {
    private Long phoneNumber;
    private boolean exists;
    private BigDecimal amount;
}

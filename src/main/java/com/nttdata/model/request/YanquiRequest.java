package com.nttdata.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YanquiRequest {
    private String userId;
    private Long dni;
    private Long phoneNumber;
    private String phoneImei;
    private String email;
    private BigDecimal amount;
}

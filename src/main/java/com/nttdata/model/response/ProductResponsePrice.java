package com.nttdata.model.response;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductResponsePrice {
    private BigDecimal salePrice;
    private BigDecimal purchasePrice;
}

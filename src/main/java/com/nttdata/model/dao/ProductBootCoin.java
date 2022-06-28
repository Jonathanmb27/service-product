package com.nttdata.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBootCoin extends AbstractDocument{

    private BigDecimal salePrice;
    private BigDecimal purchasePrice;


}

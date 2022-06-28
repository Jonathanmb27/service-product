package com.nttdata.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductYanqui extends AbstractDocument{


    private Long phoneNumber;
    private BigDecimal amount;

    /**
     * Para el caso que su monedero
     * movil pueda estar asociado
     * a una tarjeta de debito
     * */
    @DBRef
    private Product product;

}

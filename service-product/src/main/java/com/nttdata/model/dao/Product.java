package com.nttdata.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Product {

    @Id
    private String id;

    private String clientId;
    /**
     * 1: pasivo
     * 2: activo
     * */
    private int productType;

    /**
     * 1: persona
     * 2: empresa
     * */
    private int customerType;


}

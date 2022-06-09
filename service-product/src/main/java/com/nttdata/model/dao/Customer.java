package com.nttdata.model.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer")
public class Customer {
    @Id
    private String id;

    /**
     * id del cliente del microservicio de cliente
     * */
    private String clientId;

    /**
     * id para el producto
     * */
    private String productId;
}

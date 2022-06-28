package com.nttdata.model.dao;

import com.nttdata.model.dao.util.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Document
public class Product extends AbstractDocument{

    private List<String> userId;
    /**
     * 1 Personal
     * 2 Empresarial
     * */
    private ClientType clientType;

    /**
     * no soporta @DBRef en mongoDB reactivo
     * */
    @DBRef
    private List<Account> accounts;
    public Product (){
        userId=new ArrayList<>();
        accounts=new ArrayList<>();
    }
}

package com.nttdata.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private String clientId;
    /**
     * 1: pasivo
     * 2: activo
     * */
    private String productType;

    /**
     * 1: persona
     * 2: empresa
     * */
    private String customerType;

    private List<CustomerResponse> customerResponses= new ArrayList<>();
    private List<AccountResponse> accountResponses =new ArrayList<>();
}

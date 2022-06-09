package com.nttdata.service;

import com.nttdata.model.dao.Product;
import com.nttdata.model.request.ProductRequest;
import com.nttdata.model.response.ProductResponse;

import java.util.List;

public interface ProductService{

    void create(ProductRequest productRequest);
    List<ProductResponse> getProdcutFindById(String id);

}

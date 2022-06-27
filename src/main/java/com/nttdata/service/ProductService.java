package com.nttdata.service;

import com.nttdata.client.dao.Request;
import com.nttdata.client.dao.Response;
import com.nttdata.model.dao.Product;
import com.nttdata.model.request.ProductRequest;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

public interface ProductService extends AbstractService<Product>{


     Optional<Product> createProduct(ProductRequest productRequest);
     Optional<Product> findProductByClient(String id);
     Optional<ByteArrayInputStream> generateReport(String id);


}

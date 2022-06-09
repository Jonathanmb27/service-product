package com.nttdata.repository;

import com.nttdata.model.dao.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}

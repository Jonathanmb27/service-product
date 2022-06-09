package com.nttdata.repository;
import com.nttdata.model.dao.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer,String>{
}

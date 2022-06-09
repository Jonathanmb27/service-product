package com.nttdata.repository;

import com.nttdata.model.dao.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account,String>{
}

package com.nttdata.repository;

import com.nttdata.model.dao.AbstractDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AbstractRepository<T extends AbstractDocument> extends MongoRepository<T,String> {
}

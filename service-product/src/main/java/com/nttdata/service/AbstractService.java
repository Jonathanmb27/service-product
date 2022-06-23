package com.nttdata.service;

import com.nttdata.model.dao.AbstractDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AbstractService<T> {

    List<T> findAll();
    T create(T t);
    T update(T t);
    void delte(T t);

}

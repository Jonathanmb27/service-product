package com.nttdata.service.impl;

import com.nttdata.model.dao.AbstractDocument;
import com.nttdata.repository.AbstractRepository;
import com.nttdata.service.AbstractService;

import java.util.List;

public abstract class AbstractServiceImpl <T extends AbstractDocument> implements AbstractService<T> {
    public List<T> findAll(){
      return   getRepo().findAll();
    }
    public T create(T t){
        return getRepo().save(t);
    }
    public T update(T t){
        return getRepo().save(t);
    }
    public void delte(T t){
        getRepo().delete(t);
    }

    abstract AbstractRepository<T> getRepo();
}

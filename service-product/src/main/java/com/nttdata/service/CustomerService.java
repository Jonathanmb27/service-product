package com.nttdata.service;

import com.nttdata.model.dao.Account;
import com.nttdata.model.dao.Customer;

import java.util.List;

public interface CustomerService{

    List<Customer> findAll();
    void create(Customer customer);

}

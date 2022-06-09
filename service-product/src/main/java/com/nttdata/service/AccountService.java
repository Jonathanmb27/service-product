package com.nttdata.service;

import com.nttdata.model.dao.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    void create(Account account);

}

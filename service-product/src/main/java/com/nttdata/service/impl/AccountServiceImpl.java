package com.nttdata.service.impl;

import com.nttdata.model.dao.Account;

import com.nttdata.repository.AccountRepository;
import com.nttdata.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }
    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    @Override
    public void create(Account account) {
        accountRepository.save(account);
    }

}

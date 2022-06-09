package com.nttdata.service;

import com.nttdata.model.dao.Account;
import com.nttdata.model.request.AccountRequest;
import com.nttdata.model.response.AccountResponse;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> findAll();
    void create(Account account);
    Optional<AccountResponse> getAccount(String cardNumber);

    void accountUpdate(AccountRequest accountRequest);

}

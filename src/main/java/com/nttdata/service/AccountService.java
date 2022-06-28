package com.nttdata.service;

import com.nttdata.client.dao.Request;
import com.nttdata.client.dao.Response;
import com.nttdata.model.dao.Account;

import java.util.Optional;

public interface AccountService extends AbstractService<Account>{

    Optional<Account> findByCardNumber(Long cardNumber);
    Optional<Response> debitCard(Request request);
}

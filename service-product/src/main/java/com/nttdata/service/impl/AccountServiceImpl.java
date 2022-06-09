package com.nttdata.service.impl;

import com.nttdata.model.dao.Account;

import com.nttdata.model.request.AccountRequest;
import com.nttdata.model.response.AccountResponse;
import com.nttdata.repository.AccountRepository;
import com.nttdata.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<AccountResponse> getAccount(String cardNumber) {
        AccountResponse accountResponse=new AccountResponse();
         accountRepository.findAll()
                 .stream().filter(s->s.getCardNumber().equals(cardNumber))
              .findFirst()
              .ifPresent(data->{
                  String accountType="";//=null;
                  switch (data.getAccountType()){
                      case 1: accountType="Ahorro";break;
                      case 2: accountType="Cuenta corriente"; break;
                      case 3: accountType="Plazo fijo";break;
                      case 4: accountType="Personal";break;
                      case 5: accountType="Empresarial"; break;
                      case 6: accountType="Tarjeta de Credito";break;
                  }
                  accountResponse.setAccountType(accountType);
                  accountResponse.setAmount(data.getAmount());
                  accountResponse.setCardNumber(data.getCardNumber());
              });
         return Optional.of(accountResponse);
    }

    @Override
    public void accountUpdate(AccountRequest accountRequest) {

        accountRepository.findAll()
                .stream()
                .filter(s->s.getCardNumber().equals(accountRequest.getCardNumber()))
                .findFirst().ifPresent(data->{
                    data.setAmount(data.getAmount().subtract(accountRequest.getAmount()));
                    accountRepository.save(data);
               });

    }

}

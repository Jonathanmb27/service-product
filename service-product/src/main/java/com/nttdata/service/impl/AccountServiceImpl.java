package com.nttdata.service.impl;

import com.nttdata.client.dao.Request;
import com.nttdata.client.dao.Response;
import com.nttdata.model.dao.Account;
import com.nttdata.repository.AbstractRepository;
import com.nttdata.repository.AccountRepository;
import com.nttdata.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl extends AbstractServiceImpl<Account> implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }
    @Override
    AbstractRepository<Account> getRepo() {
        return accountRepository;
    }

    @Override
    public Optional<Account> findByCardNumber(Long cardNumber) {

        return  accountRepository.findAll()
                .stream()
                .filter(s->s.getCardNumber().equals(cardNumber)).findFirst();

    }

    @Override
    public Optional<Response> debitCard(Request request) {
        Optional<Account> accountOptional=accountRepository.findAll()
                .stream()
                .filter(s->s.getCardNumber().equals(request.getCardNumber()))
                .findFirst();
        Response response=new Response();
        Account accCommission;
        if(accountOptional.isPresent()){
            Account account=accountOptional.get();
            switch (request.getTypeTransaction()){
                case 1:
                case 3:
                    //Deposito
                    //Pago TC
                    account.setAmount(account.getAmount().add(request.getDebitAmount()));
                    response.setMessage("Operacion exitosa");
                    response.setResponse(true);
                    accCommission=generateCommission(account);
                    accountRepository.save(accCommission);
                    break;
                case 2:
                    //Retiro
                    if(account.getAmount().compareTo(request.getDebitAmount())>=0){
                        account.setAmount(account.getAmount().subtract(request.getDebitAmount()));
                        response.setMessage("Operacion exitosa");
                        response.setResponse(true);
                        accCommission=generateCommission(account);
                        accountRepository.save(accCommission);
                    }else {
                        response.setMessage("No cuenta con suficiente saldo");
                        response.setResponse(false);
                    }
                    break;

                case 4:
                    // Cargo consumo TC
                    if(account.getCreditLimit().compareTo(request.getDebitAmount())>=0){
                        account.setAmount(account.getAmount().subtract(request.getDebitAmount()));
                        response.setMessage("Operacion exitosa");
                        response.setResponse(true);
                        accCommission=generateCommission(account);
                        accountRepository.save(accCommission);
                    }else{
                        response.setMessage("No cuenta con suficiente saldo");
                        response.setResponse(false);
                    }
                    break;
            }
            accountRepository.save(account);
        }else
        {
            response.setMessage("No se encontro la cuenta");
            response.setResponse(false);
        }

        return Optional.of(response);
    }
    private Account generateCommission(Account account){
        if (account.getCurrentTransaction()>account.getMaxNumberTransactions()){
            // generamos una comision
            account.setCommission(account.getCommission()+account.getCommission());
        }else {
            account.setCurrentTransaction(account.getCurrentTransaction()+1);
        }
        return account;
    }
}

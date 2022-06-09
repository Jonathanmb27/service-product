package com.nttdata.client;


import com.nttdata.client.dto.ClientResult;

public interface ClientResultClient {

    ClientResult retrievePersonResult(String id);
    ClientResult retrieveCompanyResult(String id);
}

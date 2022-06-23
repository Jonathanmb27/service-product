package com.nttdata.client;

import com.nttdata.client.dao.ClientResult;

public interface ClientResultClient {

    ClientResult retrievePersonResult(String id);
    ClientResult retrieveCompanyResult(String id);
}

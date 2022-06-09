package com.nttdata.client;


import com.nttdata.client.dto.ClientResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClientResultClientImpl implements ClientResultClient{



    private  RestTemplate restTemplate=new RestTemplate();
    private final String clientHost;

    public ClientResultClientImpl(@Value("${clientHost}") final String clientHost){

        this.clientHost=clientHost;
    }
    @Override
    public ClientResult retrievePersonResult(String id) {

        return restTemplate.getForObject(clientHost+"/persons/"+id,ClientResult.class);
    }

    @Override
    public ClientResult retrieveCompanyResult(String id) {
        return restTemplate.getForObject(clientHost+"/companies/"+id,ClientResult.class);
    }
}

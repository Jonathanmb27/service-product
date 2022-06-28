package com.nttdata.util;

import com.nttdata.client.dao.Request;
import com.nttdata.client.dao.Response;
import com.nttdata.model.request.ProductRequest;

import java.math.BigDecimal;

public class ProductControllerDataTestUtils {

    public static ProductRequest getMockProductRequest(){
        ProductRequest productRequest=new ProductRequest();
        productRequest.setProductType(1);
        productRequest.setAmount(BigDecimal.valueOf(202254.32));
        productRequest.setAccountType(1);
        productRequest.setCardNumber(23666660l);
        productRequest.setUserId("125s8d5d47");
        return productRequest;
    }
    public static Request getMockRequest(){
        Request request=new Request();
        request.setCardNumber(12566584l);
        request.setDebitAmount(BigDecimal.valueOf(2154.33));
        request.setTypeTransaction(1);
        return request;
    }
    public static Response getMockResponse(){
        Response response=new Response();
        response.setResponse(true);
        response.setMessage("Successfully");
        return response;
    }
}

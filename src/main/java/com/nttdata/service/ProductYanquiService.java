package com.nttdata.service;

import com.nttdata.model.dao.ProductYanqui;
import com.nttdata.model.request.YanquiRequest;
import com.nttdata.model.response.YanquiDeposiExist;
import com.nttdata.model.response.YanquiResponse;

import java.util.Optional;

public interface ProductYanquiService extends AbstractService<ProductYanqui>{

    Optional<YanquiResponse> createdMobileWallet(YanquiRequest request);
    Optional<YanquiDeposiExist> findByPhoneNumber(Long number);
}

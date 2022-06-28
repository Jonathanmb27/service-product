package com.nttdata.service;

import com.nttdata.model.dao.ProductBootCoin;
import com.nttdata.model.response.ProductResponsePrice;

import java.util.Optional;

public interface ProductBootCoinService extends AbstractService<ProductBootCoin>{

    Optional<ProductResponsePrice> getAllBootCoin();
}

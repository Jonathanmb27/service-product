package com.nttdata.service.impl;

import com.nttdata.model.dao.ProductBootCoin;
import com.nttdata.model.response.ProductResponsePrice;
import com.nttdata.repository.AbstractRepository;
import com.nttdata.repository.ProductBootCoinRepository;
import com.nttdata.service.ProductBootCoinService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductBootCoinServiceImpl extends AbstractServiceImpl<ProductBootCoin> implements ProductBootCoinService {

    private ProductBootCoinRepository productBootCoinRepository;
    public ProductBootCoinServiceImpl(ProductBootCoinRepository productBootCoinRepository){
        this.productBootCoinRepository=productBootCoinRepository;
    }
    @Override
    AbstractRepository<ProductBootCoin> getRepo() {
        return productBootCoinRepository;
    }

    @Override
    public Optional<ProductResponsePrice> getAllBootCoin() {
        ProductResponsePrice price=new  ProductResponsePrice();
       return   productBootCoinRepository.findAll().stream().map(s->{
                price.setPurchasePrice(s.getPurchasePrice());
                price.setSalePrice(s.getSalePrice());
                return price;
        }).findFirst();

    }
}

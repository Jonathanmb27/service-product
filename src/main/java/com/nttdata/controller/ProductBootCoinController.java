package com.nttdata.controller;

import com.nttdata.handler.ClientException;
import com.nttdata.model.dao.ProductBootCoin;
import com.nttdata.model.response.ProductResponsePrice;
import com.nttdata.service.ProductBootCoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping("/bootcoin")
@RestController
public class ProductBootCoinController {
    private ProductBootCoinService productBootCoinService;
    public ProductBootCoinController(ProductBootCoinService productBootCoinService){
        this.productBootCoinService=productBootCoinService;
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductBootCoin> createBootCoin(@RequestBody ProductBootCoin productBootCoin){
        ProductBootCoin productBootCoi=productBootCoinService.create(productBootCoin);
        if(productBootCoi!=null) return Mono.just(productBootCoi);
        else throw new ClientException(HttpStatus.INTERNAL_SERVER_ERROR,"Se produjo un error al crear producto");
    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductBootCoin> updateBootCoin(@RequestBody ProductBootCoin productBootCoin){
        ProductBootCoin productBootCoi= productBootCoinService.update(productBootCoin);
        if(productBootCoi!=null) return Mono.just(productBootCoi);
        else throw new ClientException(HttpStatus.INTERNAL_SERVER_ERROR,"Se produjo un error al actualizar producto");
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductResponsePrice> getProductCoin(){
      return Mono.just(productBootCoinService.getAllBootCoin().get());
    }
}

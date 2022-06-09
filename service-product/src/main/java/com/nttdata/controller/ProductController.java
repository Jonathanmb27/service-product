package com.nttdata.controller;

import com.nttdata.model.request.AccountRequest;
import com.nttdata.model.request.ProductRequest;
import com.nttdata.model.response.AccountResponse;
import com.nttdata.model.response.ProductResponse;
import com.nttdata.service.AccountService;
import com.nttdata.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {
    private final ProductService productService;
    private final AccountService accountService;
    public ProductController(ProductService productService,AccountService accountService){
        this.productService=productService;
        this.accountService=accountService;
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody ProductRequest request){
        productService.create(request);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProductFindById(@PathVariable String id){
      return   productService.getProdcutFindById(id);
    }

    /**
     * controladores que seran llamdos por el microservicio de consumo
     * */
    @GetMapping(path = "/card/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse getAccount(@PathVariable String id){
      return   accountService.getAccount(id).get();
    }

    @PutMapping(path = "/card", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccount(@RequestBody AccountRequest accountRequest){
             accountService.accountUpdate(accountRequest);
    }
}

package com.nttdata.controller;

import com.nttdata.client.dao.Request;
import com.nttdata.client.dao.Response;
import com.nttdata.model.dao.Account;
import com.nttdata.model.dao.Product;
import com.nttdata.model.request.ProductRequest;
import com.nttdata.service.AccountService;
import com.nttdata.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.io.ByteArrayInputStream;
import java.util.Optional;

@RequestMapping("/products")
@RestController
public class ProductController {
    private final Logger LOGGER= LoggerFactory.getLogger("ProductController");
    private final ProductService productService;
    private final AccountService accountService;

    public ProductController(ProductService productService,AccountService accountService){
        this.productService=productService;
        this.accountService=accountService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> create(@RequestBody ProductRequest productRequest){

        Optional<Product> product=productService.createProduct(productRequest);
        if(product.isPresent()) return Mono.just(product.get());
         else throw new  RuntimeException();
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Product> findAll(){
        return Flux.fromIterable(productService.findAll());
    }

    @GetMapping(path = "/{car}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Account> findByCardNumber(@PathVariable("car") Long cardNumber){
       Optional<Account> account= accountService.findByCardNumber(cardNumber);
       if(account.isPresent())
           return Mono.just(account.get());
       else throw new RuntimeException();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Response> debitCard(@RequestBody Request request){
        Optional<Response> response=accountService.debitCard(request);
        if (response.isPresent()) return Mono.just(response.get());
        else throw new RuntimeException();
    }

    @GetMapping(path = "/client/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> findProductByClient(@PathVariable String id){
         Optional<Product> product= productService.findProductByClient(id);
         if(product.isPresent()) return Mono.just(product.get());
         else throw new RuntimeException();
    }

    @GetMapping(path = "/report/{clientId}",produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> summaryAverageBalances(@PathVariable("clientId")String id){
       Optional <ByteArrayInputStream> bis=productService.generateReport(id);
       if(bis.isPresent()){
           HttpHeaders headers=new HttpHeaders();
           headers.add("Content-Disposition","inline; filename=clientes.pdf");
           return ResponseEntity.ok()
                   .headers(headers)
                   .contentType(MediaType.APPLICATION_PDF)
                   .body(new InputStreamResource(bis.get()));
       }else throw new RuntimeException();
    }
}

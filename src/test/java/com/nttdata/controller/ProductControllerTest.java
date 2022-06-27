package com.nttdata.controller;

import com.nttdata.client.dao.Request;
import com.nttdata.client.dao.Response;
import com.nttdata.model.dao.Account;
import com.nttdata.model.dao.Product;
import com.nttdata.model.request.ProductRequest;
import com.nttdata.service.AccountService;
import com.nttdata.service.ProductService;
import com.nttdata.util.ProductControllerDataTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebFluxTest
//@ExtendWith(SpringExtension.class)
public class ProductControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ProductService productService;

    @MockBean
    AccountService accountService;

    ProductRequest productRequest;
    Request request;
    Response response;
    @BeforeEach
    void setUp(){
        productRequest= ProductControllerDataTestUtils.getMockProductRequest();
        response=ProductControllerDataTestUtils.getMockResponse();
        request=ProductControllerDataTestUtils.getMockRequest();
    }
    @Test
    void createProductTest(){
        Mockito.when(productService.createProduct(productRequest))
                .thenReturn(Optional.of(new Product()));
        webTestClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(productRequest),ProductRequest.class)
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    void findAllProductTest(){
        Mockito.when(accountService.findAll()).thenReturn(new ArrayList<>());
        webTestClient.get()
                .uri("/products")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    void debitCardTest(){
        Mockito.when(accountService.debitCard(request))
                .thenReturn(Optional.of(response));
        webTestClient.put()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request),Request.class)
                .exchange()
                .expectStatus().isOk();
    }
}

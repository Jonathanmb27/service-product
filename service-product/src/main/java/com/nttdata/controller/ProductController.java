package com.nttdata.controller;

import com.nttdata.model.request.ProductRequest;
import com.nttdata.model.response.ProductResponse;
import com.nttdata.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService=productService;
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
}

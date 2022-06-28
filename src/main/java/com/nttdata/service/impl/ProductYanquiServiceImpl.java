package com.nttdata.service.impl;

import com.nttdata.events.EventDispatcher;
import com.nttdata.events.Product;
import com.nttdata.model.dao.ProductYanqui;
import com.nttdata.model.request.YanquiRequest;
import com.nttdata.model.response.YanquiDeposiExist;
import com.nttdata.model.response.YanquiResponse;
import com.nttdata.repository.AbstractRepository;
import com.nttdata.repository.ProductYanquiRepository;
import com.nttdata.service.ProductYanquiService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductYanquiServiceImpl extends AbstractServiceImpl<ProductYanqui> implements ProductYanquiService {

    private final EventDispatcher eventDispatcher;
    private final  ProductYanquiRepository productYanquiRepository;
    public ProductYanquiServiceImpl(ProductYanquiRepository productYanquiRepository,EventDispatcher eventDispatcher
                                    ){
        this.productYanquiRepository=productYanquiRepository;
        this.eventDispatcher=eventDispatcher;

    }


    @Override
    AbstractRepository<ProductYanqui> getRepo() {
        return productYanquiRepository;
    }

    @Override
    public Optional<YanquiResponse> createdMobileWallet(YanquiRequest request) {
        Product product=new Product();

        product.setDni(request.getDni());
        product.setEmail(request.getEmail());
        product.setPhoneNumber(request.getPhoneNumber());
        product.setPhoneImei(request.getPhoneImei());

        ProductYanqui yanqui=new ProductYanqui();
        yanqui.setAmount(request.getAmount());
        yanqui.setCreatedAt(LocalDateTime.now());
        yanqui.setPhoneNumber(request.getPhoneNumber());

        /**
         * Verificamos la existencia del producto
         * */
        if(!verifyExistenceProduct(request.getPhoneNumber())){
            Optional<ProductYanqui> productYanqui=Optional.of(productYanquiRepository.save(yanqui));

            if(productYanqui.isPresent()){
                eventDispatcher.publish(product);

                return  Optional.of(new YanquiResponse("successfully"));
            }
            else return Optional.of(new YanquiResponse("Error"));
        }else return Optional.of(new YanquiResponse("Cuenta Existente"));


    }

    @Cacheable(value = "yanquisCache", key = "#number")
    @Override
    public Optional<YanquiDeposiExist> findByPhoneNumber(Long number) {

       Optional<ProductYanqui>productYanqui= productYanquiRepository.findAll()
               .stream()
               .filter(s->s.getPhoneNumber().equals(number)).findFirst();
        YanquiDeposiExist yanquiDeposiExist=new YanquiDeposiExist();
        yanquiDeposiExist.setPhoneNumber(number);
        if(productYanqui.isPresent()){
            yanquiDeposiExist.setExists(true);
            yanquiDeposiExist.setAmount(productYanqui.get().getAmount());
        }else{yanquiDeposiExist.setExists(false);}
        return Optional.of(yanquiDeposiExist);
    }

    private boolean verifyExistenceProduct(Long numberPhone){
       return productYanquiRepository.findAll()
               .stream()
               .filter(s->s.getPhoneNumber().equals(numberPhone))
               .findFirst().isPresent();
    }
}

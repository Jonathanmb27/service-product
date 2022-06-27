package com.nttdata.events;

import com.nttdata.service.ProductYanquiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    private final ProductYanquiService productYanquiService;
    public EventHandler(ProductYanquiService productYanquiService){
        this.productYanquiService=productYanquiService;
    }
    private final Logger LOGGER= LoggerFactory.getLogger("EventHandler");
    @KafkaListener(
            topics = "deposit",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "grupo2")
    public void consumer(Event<?> event){
        if(event.getClass().isAssignableFrom(ProductDepositEvent.class)){
            ProductDepositEvent productDepositEvent=(ProductDepositEvent)event;
            LOGGER.info("# Number {}",productDepositEvent.getData().getPhoneNumber());
            debitDigitalWallet(productDepositEvent.getData());
        }

    }
    private void debitDigitalWallet(Deposit deposit){
        LOGGER.info("# data wallet: "+deposit.getPhoneNumber());
        productYanquiService.findAll()
                .stream()
                .filter(s->s.getPhoneNumber().equals(deposit.getPhoneNumber())).findFirst().ifPresent(data->{
                    if(deposit.getOperationType()==1){
                        data.setAmount(data.getAmount().add(deposit.getAmount()));
                        productYanquiService.create(data);
                    }else{
                        data.setAmount(data.getAmount().subtract(deposit.getAmount()));
                        productYanquiService.create(data);
                    }
                });


    }


}

package com.nttdata.events;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EventDispatcher {

    private KafkaTemplate<String,Event<?>> kafkaTemplate;
    private String topicProduct="product";
    public EventDispatcher(KafkaTemplate<String,Event<?>> kafkaTemplate/* @Value("${topic.product}") String topicProduct*/){
        this.kafkaTemplate=kafkaTemplate;
       // this.topicProduct=topicProduct;
    }
    public void publish(Product product){
        ProductCreatedEvent createdEvent=new ProductCreatedEvent();
        createdEvent.setId(UUID.randomUUID().toString());
        createdEvent.setCreatedAt(LocalDateTime.now());
        createdEvent.setData(product);

        this.kafkaTemplate.send(topicProduct,createdEvent);
    }
}

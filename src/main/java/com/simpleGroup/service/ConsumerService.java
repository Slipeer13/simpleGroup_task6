package com.simpleGroup.service;

import com.simpleGroup.entity.Consumer;
import com.simpleGroup.entity.Product;

import java.util.List;

public interface ConsumerService {
    List<Consumer> findAllConsumers();

    Consumer findByIdConsumer(long id);

    void deleteByIdConsumer(long id);

    void saveOrUpdateConsumer(Consumer consumer);

    List<Product> findAllProductsByConsumer(long id);

    void saveProductToCart(Long consumerId, Product product);

}

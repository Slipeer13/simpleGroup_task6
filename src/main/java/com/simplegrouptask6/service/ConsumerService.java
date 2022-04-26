package com.simplegrouptask6.service;

import com.simplegrouptask6.entity.Consumer;
import com.simplegrouptask6.entity.Product;

import java.util.List;

public interface ConsumerService {
    List<Consumer> findAllConsumers();

    Consumer findByIdConsumer(long id);

    void deleteByIdConsumer(long id);

    void saveOrUpdateConsumer(Consumer consumer);

    void saveProductToCart(Long consumerId, Product product);

    List<Product> findAllProducts();

    Product findByIdProduct(Long id);
}

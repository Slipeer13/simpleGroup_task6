package com.simpleGroup.dao;

import com.simpleGroup.entity.Consumer;
import com.simpleGroup.entity.Product;

import java.util.List;

public interface ConsumerDAO {
    List<Consumer> findAll();

    Consumer findById(long id);

    void deleteById(long id);

    void saveOrUpdate(Consumer consumer);

    List<Product> findAllProductsByConsumer(long id);

    void saveProductToCart(Long consumerId, Product product);

}

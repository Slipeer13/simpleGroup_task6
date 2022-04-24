package com.simpleGroup.dao;

import com.simpleGroup.entity.Consumer;
import com.simpleGroup.entity.Product;

import java.util.List;

public interface ProductDAO {

    List<Product> findAll();

    Product findById(long id);

    void deleteById(long id);

    void saveOrUpdate(Product product);

    List<Consumer> findAllConsumersByProduct(long id);

}

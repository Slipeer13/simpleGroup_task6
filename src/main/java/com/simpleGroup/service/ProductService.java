package com.simpleGroup.service;

import com.simpleGroup.entity.Consumer;
import com.simpleGroup.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    Product findByIdProduct(long id);

    void deleteByIdProduct(long id);

    void saveOrUpdateProduct(Product product);

    Boolean checkProductByTitleAndPrice(String title, Integer price);

    Consumer findByIdConsumer(Long id);

}

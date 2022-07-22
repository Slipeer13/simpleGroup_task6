package com.simplegrouptask6.service;

import com.simplegrouptask6.entity.Product;
import com.simplegrouptask6.entity.Purchase;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    Product findByIdProduct(long id);

    void deleteByIdProduct(long id);

    void saveOrUpdateProduct(Product product);

    Product findProductByTitle(String title);

    List<Purchase> findAllProductsByConsumerId(Long consumerId);
}

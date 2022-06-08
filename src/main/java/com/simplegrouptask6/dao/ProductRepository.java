package com.simplegrouptask6.dao;

import com.simplegrouptask6.entity.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product findById(long id);

    void deleteProduct(Product product);

    void save(Product product);

    void update(Product product);

    Product findProductByTitle(String title);

}

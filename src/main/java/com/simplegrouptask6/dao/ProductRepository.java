package com.simplegrouptask6.dao;

import com.simplegrouptask6.entity.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product findById(long id);

    void deleteProduct(Product product);

    void saveOrUpdate(Product product);

    Boolean checkProductByTitleAndPrice(String title, Integer price);

}

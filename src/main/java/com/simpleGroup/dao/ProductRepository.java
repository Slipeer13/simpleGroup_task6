package com.simpleGroup.dao;

import com.simpleGroup.entity.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product findById(long id);

    void deleteById(long id);

    void saveOrUpdate(Product product);

    Boolean checkProductByTitleAndPrice(String title, Integer price);

}

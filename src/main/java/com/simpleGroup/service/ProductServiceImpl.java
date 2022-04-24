package com.simpleGroup.service;

import com.simpleGroup.dao.ProductDAO;
import com.simpleGroup.entity.Consumer;
import com.simpleGroup.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductDAO productDAO;

    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    @Transactional
    public List<Product> findAllProducts() {
        return productDAO.findAll();
    }

    @Override
    @Transactional
    public Product findByIdProduct(long id) {
        return productDAO.findById(id);
    }

    @Override
    @Transactional
    public void deleteByIdProduct(long id) {
        productDAO.deleteById(id);
    }

    @Override
    @Transactional
    public void saveOrUpdateProduct(Product product) {
        productDAO.saveOrUpdate(product);
    }

    @Override
    @Transactional
    public List<Consumer> findAllConsumersByProduct(long id) {
        return productDAO.findAllConsumersByProduct(id);
    }

}

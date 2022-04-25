package com.simpleGroup.service;

import com.simpleGroup.dao.ConsumerRepository;
import com.simpleGroup.dao.ProductRepository;
import com.simpleGroup.entity.Consumer;
import com.simpleGroup.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ConsumerRepository consumerRepository;

    @Autowired
    public void setProductDAO(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Autowired
    public void setConsumerDAO(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    @Override
    @Transactional
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product findByIdProduct(long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteByIdProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveOrUpdateProduct(Product product) {
        if(!product.getTitle().isEmpty() && product.getPrice() > 0) {
            Boolean exist = checkProductByTitleAndPrice(product.getTitle(), product.getPrice());
            if (!exist) {
                productRepository.saveOrUpdate(product);
            }
        }
    }

    @Override
    @Transactional
    public Boolean checkProductByTitleAndPrice(String title, Integer price) {
        return productRepository.checkProductByTitleAndPrice(title, price);
    }

    @Override
    @Transactional
    public Consumer findByIdConsumer(Long id) {
        return consumerRepository.findById(id);
    }

}

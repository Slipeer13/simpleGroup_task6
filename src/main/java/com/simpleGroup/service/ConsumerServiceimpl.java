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
public class ConsumerServiceimpl implements ConsumerService{
    private ConsumerRepository consumerRepository;
    private ProductRepository productRepository;

    @Autowired
    public void setConsumerDAO(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }
    @Autowired
    public void setProductDAO(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<Consumer> findAllConsumers() {
        return consumerRepository.findAll();
    }

    @Override
    @Transactional
    public Consumer findByIdConsumer(long id) {
        return consumerRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteByIdConsumer(long id) {
        consumerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveOrUpdateConsumer(Consumer consumer) {
        if(!consumer.getName().isEmpty()) {
            Boolean exist = checkConsumerToDB(consumer);
            if (!exist) {
                consumerRepository.saveOrUpdate(consumer);
            }
        }
    }

    private Boolean checkConsumerToDB(Consumer consumer) {
        return consumerRepository.checkConsumerToDB(consumer);
    }

    @Override
    @Transactional
    public void saveProductToCart(Long consumerId, Product product) {
        Consumer consumer = findByIdConsumer(consumerId);
        consumer.getProducts().add(product);
        saveOrUpdateConsumer(consumer);
    }

    @Override
    @Transactional
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product findByIdProduct(Long id) {
        return productRepository.findById(id);
    }
}

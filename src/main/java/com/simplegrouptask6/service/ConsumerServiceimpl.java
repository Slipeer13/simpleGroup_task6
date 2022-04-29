package com.simplegrouptask6.service;

import com.simplegrouptask6.dao.ConsumerRepository;
import com.simplegrouptask6.dao.ProductRepository;
import com.simplegrouptask6.entity.Consumer;
import com.simplegrouptask6.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//todo Для продуктов поправил название класса сервиса, для потребителей нет.
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

    //todo А если имя null? Или не может быть?
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

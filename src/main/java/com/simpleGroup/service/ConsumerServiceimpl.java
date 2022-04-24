package com.simpleGroup.service;

import com.simpleGroup.dao.ConsumerDAO;
import com.simpleGroup.entity.Consumer;
import com.simpleGroup.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConsumerServiceimpl implements ConsumerService{
    private ConsumerDAO consumerDAO;

    @Autowired
    public void setConsumerDAO(ConsumerDAO consumerDAO) {
        this.consumerDAO = consumerDAO;
    }

    @Override
    @Transactional
    public List<Consumer> findAllConsumers() {
        return consumerDAO.findAll();
    }

    @Override
    @Transactional
    public Consumer findByIdConsumer(long id) {
        return consumerDAO.findById(id);
    }

    @Override
    @Transactional
    public void deleteByIdConsumer(long id) {
        consumerDAO.deleteById(id);
    }

    @Override
    @Transactional
    public void saveOrUpdateConsumer(Consumer consumer) {
        consumerDAO.saveOrUpdate(consumer);
    }

    @Override
    @Transactional
    public List<Product> findAllProductsByConsumer(long id) {
        return consumerDAO.findAllProductsByConsumer(id);
    }

    @Override
    @Transactional
    public void saveProductToCart(Long consumerId, Product product) {
        consumerDAO.saveProductToCart(consumerId, product);
    }
}

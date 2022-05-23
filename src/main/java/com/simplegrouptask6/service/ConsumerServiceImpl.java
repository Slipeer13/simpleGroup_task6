package com.simplegrouptask6.service;

import com.simplegrouptask6.dao.ConsumerRepository;
import com.simplegrouptask6.dao.OrderRepository;
import com.simplegrouptask6.dao.ProductRepository;
import com.simplegrouptask6.entity.Consumer;
import com.simplegrouptask6.entity.Order;
import com.simplegrouptask6.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService{

    private ConsumerRepository consumerRepository;
    private ProductService productService;
    private OrderRepository orderRepository;

    @Autowired
    public void setConsumerRepository(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public List<Consumer> findAllConsumers() {
        return consumerRepository.findAll();
    }

    @Override
    @Transactional
    public Consumer findByIdConsumer(long id) {
        Consumer consumer = consumerRepository.findById(id);
        if(consumer == null) {
            throw new EntityNotFoundException("There is no consumer with id = " + id);
        }
        return consumer;
    }

    //todo См. deleteByIdProduct
    @Override
    @Transactional
    public void deleteByIdConsumer(long id) {
        findByIdConsumer(id);
        consumerRepository.deleteById(id);
    }

    //todo Перепиши без вложенности.
    @Override
    @Transactional
    public void saveOrUpdateConsumer(Consumer consumer) {
        if (consumer != null) {
            Boolean exist = checkConsumerToDB(consumer);
            if (!exist) {
                consumerRepository.saveOrUpdate(consumer);
            }
            else throw new EntityExistsException("there is such a consumer in database");
        }
        else {
            throw new EntityNotFoundException("the consumer is null");
        }
    }

    @Override
    @Transactional
    public Boolean checkConsumerToDB(Consumer consumer) {
        return consumerRepository.checkConsumerToDB(consumer);
    }

    @Override
    @Transactional
    public void saveProductToCart(Long consumerId, Product product) {
        Consumer consumer = findByIdConsumer(consumerId);
        Order orderToDB = orderRepository.findByConsumerAndProduct(consumer, product);
        if(orderToDB == null) {
            Order order = new Order();
            order.setConsumer(consumer);
            order.setProduct(product);
            order.setQuantity(1);
            orderRepository.saveOrUpdate(order);
        }
        else {
            orderToDB.setQuantity(orderToDB.getQuantity() + 1);
            orderRepository.saveOrUpdate(orderToDB);
        }
    }

    @Override
    @Transactional
    public List<Product> findAllProducts() {
        return productService.findAllProducts();
    }

    @Override
    @Transactional
    public Product findByIdProduct(Long id) {
        return productService.findByIdProduct(id);
    }
}

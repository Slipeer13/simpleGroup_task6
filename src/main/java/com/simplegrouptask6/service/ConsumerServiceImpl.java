package com.simplegrouptask6.service;

import com.simplegrouptask6.dao.ConsumerRepository;
import com.simplegrouptask6.dao.PurchaseRepository;
import com.simplegrouptask6.entity.Consumer;
import com.simplegrouptask6.entity.Purchase;
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
    private PurchaseRepository purchaseRepository;

    @Autowired
    public void setConsumerRepository(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    @Autowired
    public void setOrderRepository(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
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

    @Override
    @Transactional
    public void deleteByIdConsumer(long id) {
        consumerRepository.deleteConsumer(findByIdConsumer(id));
    }

    @Override
    @Transactional
    public void saveOrUpdateConsumer(Consumer consumer) {
        if (consumer == null) {
            throw new EntityNotFoundException("the consumer is null");
        }
        Boolean exist = checkConsumerToDB(consumer);
        if (exist) {
            throw new EntityExistsException("there is such a consumer in database");
        }
        consumerRepository.saveOrUpdate(consumer);
    }

    @Override
    @Transactional
    public Boolean checkConsumerToDB(Consumer consumer) {
        return consumerRepository.checkConsumerToDB(consumer);
    }

    @Override
    @Transactional
    public List<Consumer> findAllConsumersByProductId(Long id) {
        Product product = findByIdProduct(id);
        return consumerRepository.findAllConsumersByProductId(product);
    }

    @Override
    @Transactional
    public void saveProductToCart(Long consumerId, Long productId) {
        Product product = findByIdProduct(productId);
        Consumer consumer = findByIdConsumer(consumerId);
        Purchase purchaseToDB = purchaseRepository.findByConsumerAndProduct(consumer, product);
        if(purchaseToDB == null) {
            Purchase purchase = new Purchase();
            purchase.setConsumer(consumer);
            purchase.setPrice(product.getPrice());
            purchase.setTitle(product.getTitle());
            purchase.setQuantity(1);
            purchaseRepository.saveOrUpdate(purchase);
        }
        else {
            purchaseToDB.setQuantity(purchaseToDB.getQuantity() + 1);
            purchaseRepository.saveOrUpdate(purchaseToDB);
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

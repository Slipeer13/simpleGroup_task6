package com.simplegrouptask6.service;

import com.simplegrouptask6.dao.ProductRepository;
import com.simplegrouptask6.entity.Consumer;
import com.simplegrouptask6.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ConsumerService consumerService;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Autowired
    public void setConsumerService(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Override
    @Transactional
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product findByIdProduct(long id) {
        Product product = productRepository.findById(id);
        if(product == null) {
            throw new EntityNotFoundException("There is no product with id = " + id);
        }
        return product;
    }

    @Override
    @Transactional
    public void deleteByIdProduct(long id) {
        productRepository.deleteProduct(findByIdProduct(id));
    }

    @Override
    @Transactional
    //todo Если брать в расчёт п.4* задания, то метод должен работать по другому.
    // Мне кажется, что логика должна быть такая:
    //      * Если есть id у продукта, значит это апдейт, в таком случае продукт нужно обновлять.
    //          Тут нужно думать, можно ли менять название у этого продукта?
    //          Наверное можно, но нужно отслеживать, чтобы оно не совпадало с остальными продуктами в магазине.
    //          Стоимость однозначно может меняться.
    //      * Если нет id у продукта, то это однозначно добавление нового продукта.
    //          Нужно добавлять, но нужно отслеживать на уникальность названий.
    //          Эти ограничения можно реализовать на уровне базы, можно на уровне кода.
    public void saveOrUpdateProduct(Product product) {
        if (product == null) {
            throw new EntityNotFoundException("the product is null");
        }
        if (product.getId() == null) {
            productRepository.save(product);

        } else {
            Product productFromDB = findProductByTitle(product.getTitle());
            if (productFromDB == null || productFromDB.equals(product)) {
                productRepository.update(product);
            } else {
                throw new EntityExistsException("there is such a product in database");
            }
        }
    }

    @Override
    @Transactional
    public Product findProductByTitle(String title) {
        return productRepository.findProductByTitle(title);
    }

    @Override
    @Transactional
    public Consumer findByIdConsumer(Long id) {
        return consumerService.findByIdConsumer(id);
    }


}

package com.simplegrouptask6.service;

import com.simplegrouptask6.dao.ConsumerRepository;
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
        findByIdProduct(id);
        productRepository.deleteById(id);
    }

    //todo Если title = null будет падать NPE скорее всего. Или невозможно попасть в этот метод с title = null?
    //теперь product==null не попадёт
    @Override
    @Transactional
    public void saveOrUpdateProduct(Product product) {
        if (product != null) {
            Boolean exist = checkProductByTitleAndPrice(product.getTitle(), product.getPrice());
            if (!exist) {
                productRepository.saveOrUpdate(product);
            }
            else throw new EntityExistsException("there is such a product in database");
        }
        else {
            throw new EntityNotFoundException("the product is null");
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
        return consumerService.findByIdConsumer(id);
    }


}

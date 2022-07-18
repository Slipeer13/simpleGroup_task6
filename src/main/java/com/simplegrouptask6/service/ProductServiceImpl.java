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
    public void saveOrUpdateProduct(Product product) {
        if (product == null) {
            throw new EntityNotFoundException("the product is null");
        }
        Product productFromDB = findProductByTitle(product.getTitle());
        if (product.getId() == null && productFromDB == null) {
            productRepository.save(product);
        } else {
            if (productFromDB == null || productFromDB.equals(product)) {
                productRepository.update(product);
            } else {
                throw new EntityExistsException("there is such a product in database");
            }
        }
    }

    @Override
    @Transactional
    //todo Обрати внимание на модификаторы доступа у методов сервиса. Это же касается и сервиса покупателей.
    // Например, этот метод используется только в этом классе.
    // Зачем он в интерфейсе, если извне никакие классы этот метод не используют? Зачем он публичный?
    public Product findProductByTitle(String title) {
        return productRepository.findProductByTitle(title);
    }

    @Override
    @Transactional
    //todo Если посмотреть на метод findAllConsumersByProductId в ConsumerServiceImpl,
    // то можно ведь сделать подобным образом. Только сначала там избавиться от 2-х запросов, один из которых лишний.
    // Сджойнить отношения продукта и покупок и выбрать нужные по id потребителя.
    // И тогда не придётся между собой связывать сервисы потребителя и продукта.
    public Consumer findByIdConsumer(Long id) {
        return consumerService.findByIdConsumer(id);
    }


}

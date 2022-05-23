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
    //todo Получается, ты сейчас 3 раза ходишь в базу, чтобы удалить продукт.
    // Сначала в методе сервиса findByIdProduct ты получаешь продукт по id, чтобы не удалять null продукт
    // Затем в методе репозитория deleteById ты опять достаёшь продукт по id.
    // Почему бы просто не сделать в репо метод deleteProduct(Product product)?
    // Хибер, наверное, не полезет непосредственно в базу за этим продуктом второй раз,
    // т.к. он уже получен в рамках этой же транзакции. Но всё равно, зачем его ещё раз получать по id, если он уже есть?
    public void deleteByIdProduct(long id) {
        findByIdProduct(id);
        productRepository.deleteById(id);
    }

    //todo На мой взгляд лучше переписать без вложенности условий, читаться будет лучше, например:
    //      * сначала проверяешь, на null. Если null, бросаешь EntityNotFoundException
    //      * вторым условием проверяешь на наличие, if (exists), бросаешь EntityExistsException
    //      * без условий сохраняешь запись.
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

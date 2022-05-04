package com.simplegrouptask6.dao;

import com.simplegrouptask6.entity.Consumer;
import com.simplegrouptask6.entity.Order;
import com.simplegrouptask6.entity.Product;

public interface OrderRepository {
    Order findByConsumerAndProduct(Consumer consumer, Product product);

    void saveOrUpdate(Order order);
}

package com.simplegrouptask6.dao;

import com.simplegrouptask6.entity.Consumer;
import com.simplegrouptask6.entity.Purchase;
import com.simplegrouptask6.entity.Product;

public interface PurchaseRepository {
    Purchase findByConsumerAndProduct(Consumer consumer, Product product);

    void saveOrUpdate(Purchase purchase);
}

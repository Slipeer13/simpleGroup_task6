package com.simplegrouptask6.dao;

import com.simplegrouptask6.entity.Consumer;
import com.simplegrouptask6.entity.Purchase;
import com.simplegrouptask6.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseRepositoryImpl implements PurchaseRepository {

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Purchase findByConsumerAndProduct(Consumer consumer, Product product) {
        Session session = sessionFactory.getCurrentSession();
        Query<Purchase> query = session.createQuery("from Purchase where consumer =:consumer and product =:product", Purchase.class);
        query.setParameter("consumer", consumer);
        query.setParameter("product", product);
        return query.list().isEmpty() ? null : query.getSingleResult();
    }

    @Override
    public void saveOrUpdate(Purchase purchase) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(purchase);
    }
}

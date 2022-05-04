package com.simplegrouptask6.dao;

import com.simplegrouptask6.entity.Consumer;
import com.simplegrouptask6.entity.Order;
import com.simplegrouptask6.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository{

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order findByConsumerAndProduct(Consumer consumer, Product product) {
        Session session = sessionFactory.getCurrentSession();
        Query<Order> query = session.createQuery("from Order where consumer =:consumer and product =:product", Order.class);
        query.setParameter("consumer", consumer);
        query.setParameter("product", product);
        Order order = query.getResultList().size() > 0 ? query.getResultList().get(0) : null;
        return order;
    }

    @Override
    public void saveOrUpdate(Order order) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(order);
    }
}

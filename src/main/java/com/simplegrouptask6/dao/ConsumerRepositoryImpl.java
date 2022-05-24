package com.simplegrouptask6.dao;

import com.simplegrouptask6.entity.Consumer;
import com.simplegrouptask6.entity.Order;
import com.simplegrouptask6.entity.Product;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConsumerRepositoryImpl implements ConsumerRepository {
    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Consumer> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Consumer", Consumer.class).getResultList();
    }

    @Override
    public Consumer findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Consumer consumer = session.get(Consumer.class, id);
        if(consumer != null) {
            Hibernate.initialize(consumer.getOrders());
        }
        return consumer;
    }

    @Override
    public void deleteConsumer(Consumer consumer) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(consumer);
    }

    @Override
    public void saveOrUpdate(Consumer consumer) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(consumer);
    }

    @Override
    public Boolean checkConsumerToDB(Consumer consumer) {
        Session session = sessionFactory.getCurrentSession();
        Query<Consumer> query = session.createQuery("from Consumer where name =:consumerName", Consumer.class);
        query.setParameter("consumerName", consumer.getName());
        return query.getResultList().size() > 0;
    }

    @Override
    public List<Consumer> findAllConsumersByProductId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Consumer> query = session.createQuery("select c from Consumer c inner join c.orders o where o.product.id =:productId", Consumer.class);
        query.setParameter("productId", id);
        return query.getResultList();
    }
}

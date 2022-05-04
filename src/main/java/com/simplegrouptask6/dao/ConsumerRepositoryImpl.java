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

//todo Название класса не корректное.
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
        List<Order> orders = consumer.getOrders();
        if(orders != null) {
            Hibernate.initialize(orders);
        }

        return consumer;
    }

    @Override
    public void deleteById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Consumer consumer = findById(id);
        session.delete(consumer);
        //todo Смотри комменты в репо продукта.
        //теперь, если CascadeType.ALL, то удалятся и сущности из связанных таблиц

       /* Query<Product> query = session.createQuery("delete from Consumer where id =:consumerId");
        query.setParameter("consumerId", id);
        query.executeUpdate();*/
    }

    @Override
    public void saveOrUpdate(Consumer consumer) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(consumer);
    }

    //todo Почему алиас а,?
    @Override
    public Boolean checkConsumerToDB(Consumer consumer) {
        Session session = sessionFactory.getCurrentSession();
        Query<Consumer> query = session.createQuery("from Consumer where name =:consumerName", Consumer.class);
        query.setParameter("consumerName", consumer.getName());
        return query.getResultList().size() > 0;
    }
}

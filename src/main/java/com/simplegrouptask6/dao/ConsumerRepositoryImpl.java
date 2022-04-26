package com.simplegrouptask6.dao;

import com.simplegrouptask6.entity.Consumer;
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

    //todo Почему алиас "a"?
    //      Вернуть можно сразу результат запроса.
    @Override
    public List<Consumer> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Consumer", Consumer.class).getResultList();
    }

    @Override
    public Consumer findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Consumer consumer = session.get(Consumer.class, id);
        Hibernate.initialize(consumer.getProducts());
        return consumer;
    }

    @Override
    public void deleteById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("delete from Consumer where id =:consumerId");
        query.setParameter("consumerId", id);
        query.executeUpdate();
    }

    @Override
    public void saveOrUpdate(Consumer consumer) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(consumer);
    }

    @Override
    public Boolean checkConsumerToDB(Consumer consumer) {
        Session session = sessionFactory.getCurrentSession();
        Query<Consumer> query = session.createQuery("Select a from Consumer a where a.name =:consumerName", Consumer.class);
        query.setParameter("consumerName", consumer.getName());
        return query.getResultList().size() > 0;
    }

    //todo См. аналогичный метод в dao продукта.


    //todo Ни к чему эта логика в dao.
    //      Если несколько продуктов нужно будет добавить в корзину,
    //      будешь ещё один метод в dao добавлять?
    //      Лучше так:
    //          Сервис получил потребителя, получил продукт.
    //          Добавил продукт в корзину потребителя.
    //          Сохранил изменения.

}

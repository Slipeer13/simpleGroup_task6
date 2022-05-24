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
    //todo Для информации.
    // Вместо query.getResultList().get(0) можно query.getSingleResult().
    // Вместо query.getResultList().size() > 0 можно CollectionsUtils.isEmpty(query.list().size())
    // Смысл в том, что так сложнее намного допустить ошибку в том числе при рефакторинге.
    // Если случайно поменять значение 0 на какое-то другое, то поведение метода полностью поменяется.
    // Придётся дебажить потом и искать ошибку в логике эту.
    // А с использованием предназначенных для конкретных ситуаций методов такие ошибки допустить значительно сложнее.
    // Даже если допустишь ошибку в написании метода, то код просто не скомпилируется.
    // Подобные ситуации относятся и к проверке на null.
    // Например, одна и та же проверка разными способами записи:
    // object == null равноценна Objects.isNull(object)
    // object != null равноценна Objects.nonNull(object)
    // isNull и nonNull по моему мнению читается лучше и ошибиться сложнее при написании.
    // Если заимпортить не класс, а метод Objects.isNull, то проверка в коде будет выглядеть просто как isNull(object)
    // Но я тебя ни в коем случае не призываю делать именно так, т.к. тут мнения делятся, как лучше проверять на null, это просто информация к размышлению.
    // И можно в одну строку возвращать результат, зачем создавать локальный объект внутри метода,
    // если он больше никак не используется, кроме как для возврата результата?
    public Order findByConsumerAndProduct(Consumer consumer, Product product) {
        Session session = sessionFactory.getCurrentSession();
        Query<Order> query = session.createQuery("from Order where consumer =:consumer and product =:product", Order.class);
        query.setParameter("consumer", consumer);
        query.setParameter("product", product);
        return query.list().isEmpty() ? null : query.getSingleResult();
    }

    @Override
    public void saveOrUpdate(Order order) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(order);
    }
}

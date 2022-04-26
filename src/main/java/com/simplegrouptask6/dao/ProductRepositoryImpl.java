package com.simplegrouptask6.dao;

import com.simplegrouptask6.entity.Product;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

//todo Некорректное название класса, переходящее из таски в таску.
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //todo Почему алиас "a" в запросе?
    //      Создавать именованную переменную в данном случае ни к чему. Можно сразу возвращать результат запроса.
    @Override
    public List<Product> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public Product findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Product product = session.get(Product.class, id);
        Hibernate.initialize(product.getConsumers());
        return product;
    }

    @Override
    public void deleteById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("delete from Product where id =:productId");
        query.setParameter("productId", id);
        query.executeUpdate();

    }

    @Override
    public void saveOrUpdate(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(product);
    }

    //todo Для чего определяется размер списка потребителей?
    //      Логика этого метода в dao не нужна. Уже есть метод findById.
    //      Сервис должен получить продукт, и вернуть список потребителей этого продукта.


    @Override
    public Boolean checkProductByTitleAndPrice(String title, Integer price) {
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("Select a from Product a where a.title =:productTitle and a.price =:productPrice", Product.class);
        query.setParameter("productTitle", title);
        query.setParameter("productPrice", price);
        return query.getResultList().size() > 0;

    }
}

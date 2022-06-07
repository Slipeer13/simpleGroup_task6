package com.simplegrouptask6.dao;

import com.simplegrouptask6.entity.Product;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Product> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public Product findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Product product = session.get(Product.class, id);
        if(product != null) {
            Hibernate.initialize(product.getOrders());
        }
        return product;
    }

    @Override
    public void deleteProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(product);
    }

    @Override
    public void saveOrUpdate(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(product);
    }

    @Override
    //todo Опять же. Если судить по п.4* не может быть продуктов с одинаковым названием и разной стоимостью.
    // Как я понимаю, цена у продукта может меняться. А значит, приложение должно вести себя по другому.
    public Boolean checkProductByTitleAndPrice(String title, Integer price) {
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("from Product where title =:productTitle and price =:productPrice", Product.class);
        query.setParameter("productTitle", title);
        query.setParameter("productPrice", price);
        return query.getResultList().size() > 0;

    }
}

package com.simplegrouptask6.dao;

import com.simplegrouptask6.entity.Product;
import com.simplegrouptask6.entity.Purchase;
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
            Hibernate.initialize(product.getPurchases());
        }
        return product;
    }

    @Override
    public void deleteProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(product);
    }

    @Override
    public void save(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.save(product);
    }

    @Override
    public void update(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(product);
    }

    @Override
    public Product findProductByTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("from Product where title =:productTitle", Product.class);
        query.setParameter("productTitle", title);
        return query.getResultList().isEmpty() ? null : query.getSingleResult();

    }

    @Override
    public List<Purchase> findAllProductsByConsumerId(Long consumerId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Purchase> query = session.createQuery("select c from Purchase c inner join c.consumer p where p.id =:consumerId", Purchase.class);
        query.setParameter("consumerId", consumerId);
        return query.getResultList();
    }
}

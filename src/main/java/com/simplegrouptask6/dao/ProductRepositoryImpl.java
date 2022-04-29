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
        Hibernate.initialize(product.getConsumers());
        return product;
    }

    @Override
    public void deleteById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Product product = findById(id);
        session.delete(product);
        //todo Чтобы этого не происходило, можно список потребителей перед удалением делать пустым.
        //      Или можно поиграться с orphanRemoval.
        //      Или можно для удаления продукта получать его не подтягивая его потребителей, инициализация же lazy.
        //теперь, если CascadeType.ALL, то удалятся и сущности из связанных таблиц

        /*Query<Product> query = session.createQuery("delete from Product where id =:productId");
        query.setParameter("productId", id);
        query.executeUpdate();*/

    }

    @Override
    public void saveOrUpdate(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(product);
    }

    //todo Опять алиас "a" для Product. Так не делают.
    @Override
    public Boolean checkProductByTitleAndPrice(String title, Integer price) {
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("Select a from Product a where a.title =:productTitle and a.price =:productPrice", Product.class);
        query.setParameter("productTitle", title);
        query.setParameter("productPrice", price);
        return query.getResultList().size() > 0;

    }
}

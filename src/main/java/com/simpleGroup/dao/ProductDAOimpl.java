package com.simpleGroup.dao;

import com.simpleGroup.entity.Consumer;
import com.simpleGroup.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//todo Некорректное название класса, переходящее из таски в таску.
@Repository
public class ProductDAOimpl implements ProductDAO{

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
        List<Product> productList = session.createQuery("Select a from Product a", Product.class).getResultList();
        return productList;
    }

    @Override
    public Product findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Product.class, id);
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
    public List<Consumer> findAllConsumersByProduct(long id) {
        Session session = sessionFactory.getCurrentSession();
        Product product = session.get(Product.class, id);
        product.getConsumerList().size();
        return product.getConsumerList();
    }
}

package com.simpleGroup.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//todo Наименование таблиц принято давать в единственном числе.
//      Опять же, нет файлов со скриптом или миграциями, чтобы оценить, как организована БД.
//      Для конструкторов так же можно использовать Lombok. Раз он появился в проекте.
@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer price;
    //todo Есть тип каскада, который объединяет все эти типы. Проще указать один, чем перечислять все.
    //      Поле корректней назвать consumers.
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "consumer_product", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "consumer_id"))
    private List<Consumer> consumers = new ArrayList<>();

    public Product() {
    }

    public Product(String title, Integer price) {
        this.title = title;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return title.equals(product.title) && price.equals(product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price);
    }
}

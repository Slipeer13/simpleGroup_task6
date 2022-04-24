package com.simpleGroup.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

//todo Наименование таблиц принято давать в единственном числе.
//      Опять же, нет файлов со скриптом или миграциями, чтобы оценить, как организована БД.
//      Для конструкторов так же можно использовать Lombok. Раз он появился в проекте.
@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer price;
    //todo Есть тип каскада, который объединяет все эти типы. Проще указать один, чем перечислять все.
    //      Поле корректней назвать consumers.
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(name = "consumer_products", joinColumns = @JoinColumn(name = "products_id"), inverseJoinColumns = @JoinColumn(name = "consumer_id"))
    private List<Consumer> consumerList;

    public Product() {
    }

    public Product(String title, Integer price) {
        this.title = title;
        this.price = price;
    }

}

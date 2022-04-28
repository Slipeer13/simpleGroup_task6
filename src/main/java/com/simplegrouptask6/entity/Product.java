package com.simplegrouptask6.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

//todo Наименование таблиц принято давать в единственном числе.
//      Опять же, нет файлов со скриптом или миграциями, чтобы оценить, как организована БД.
//      Для конструкторов так же можно использовать Lombok. Раз он появился в проекте.
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer price;
    //todo Есть тип каскада, который объединяет все эти типы. Проще указать один, чем перечислять все.
    //      Поле корректней назвать consumers.
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "consumer_product", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "consumer_id"))
    @ToString.Exclude
    private List<Consumer> consumers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

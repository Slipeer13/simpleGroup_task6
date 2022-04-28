package com.simplegrouptask6.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "consumer")
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "consumer_product", joinColumns = @JoinColumn(name = "consumer_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    @ToString.Exclude
    private List<Product> products;

    public Map<Product, Long> getProductsMap(List<Product> products) {
        Map<Product, Long> result = new HashMap<>();
        for (Product p :
             products) {
            Long count = products.stream().filter(x -> x.equals(p)).count();//возвращает кол-во продуктов в списке
            result.put(p, count);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Consumer consumer = (Consumer) o;
        return id != null && Objects.equals(id, consumer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

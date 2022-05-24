package com.simplegrouptask6.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
//todo А почему не просто order? :)
//но "order" вроде зарезервированное слово в SQL запросах(можно в запросах public.order писать, но это ещё длиннее или 'order')?, не знаю как правильнее, сделал "orders"
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return consumer.equals(order.consumer) && product.equals(order.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consumer, product);
    }
}

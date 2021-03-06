package com.simplegrouptask6.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "purchase")
//todo Судя по заданию (п. 4*), нужно стоимость товара на момент приобретения отслеживать.
// Т.е., как я понимаю, для продукта уникальным является его наименование, а стоимость может меняться по каким-то причинам.
// Вот это и нужно смоделировать. То есть, по сути, речь в задаче не про заказы (purchases), а про совершённые покупки.
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer price;

    private Integer quantity;

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
        Purchase purchase = (Purchase) o;
        return id.equals(purchase.id) && price.equals(purchase.price) && quantity.equals(purchase.quantity) && consumer.equals(purchase.consumer) && product.equals(purchase.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, quantity, consumer, product);
    }
}

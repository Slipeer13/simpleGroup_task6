package com.simplegrouptask6.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

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

    @Size(min = 2, message = "title must be min 2 symbol")
    private String title;

    @Min(value = 1, message = "price must be over 0")
    @NotNull
    private Integer price;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "product")
    List<Purchase> purchases;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

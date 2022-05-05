package com.simplegrouptask6.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
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

    @Size(min = 2, message = "name must be min 2 symbol")
    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "consumer")
    List<Order> orders;

    //todo Это бизнес-логика. Её не должно быть в сущности. Она должна быть в сервисе.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consumer consumer = (Consumer) o;
        return name.equals(consumer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

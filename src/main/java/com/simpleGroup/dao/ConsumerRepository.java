package com.simpleGroup.dao;

import com.simpleGroup.entity.Consumer;

import java.util.List;

public interface ConsumerRepository {
    List<Consumer> findAll();

    Consumer findById(long id);

    void deleteById(long id);

    void saveOrUpdate(Consumer consumer);

    Boolean checkConsumerToDB(Consumer consumer);

}

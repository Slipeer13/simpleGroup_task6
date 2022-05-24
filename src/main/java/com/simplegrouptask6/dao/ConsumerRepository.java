package com.simplegrouptask6.dao;

import com.simplegrouptask6.entity.Consumer;

import java.util.List;

public interface ConsumerRepository {
    List<Consumer> findAll();

    Consumer findById(long id);

    void deleteConsumer(Consumer consumer);

    void saveOrUpdate(Consumer consumer);

    Boolean checkConsumerToDB(Consumer consumer);

    List<Consumer> findAllConsumersByProductId(Long id);

}

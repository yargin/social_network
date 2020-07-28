package com.getjavajob.training.yarginy.socialnetwork.dao.entities;

public interface EntityFactory<E> {
    E getInstance();

    Builder<E> getBuilder();
}

package com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.nonidentifying;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Entity;

import java.util.Collection;

public interface SelfManyToManyDao<E extends Entity> {
    Collection<E> select(E entity);

    boolean create(E first, E second);

    boolean delete(E first, E second);
}

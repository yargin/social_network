package com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.identifying;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Entity;

import java.util.Collection;

public interface ManyToManyDao<F extends Entity, S extends Entity> {
    Collection<S> selectByFirst(F first);

    Collection<F> selectBySecond(S second);

    boolean create(F first, S second);

    boolean delete(F first, S second);
}

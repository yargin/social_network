package com.getjavajob.training.yarginy.socialnetwork.dao.relations.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.Entity;

import java.util.Collection;

public interface OneToManyDao<O extends Entity, M extends Entity> {
    Collection<M> selectMany(O entity);

    O selectOne(M entity);
}

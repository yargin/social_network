package com.getjavajob.training.yarginy.socialnetwork.dao.relations.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Entity;

import java.util.Collection;

public interface OneToManyDao<O extends Entity, M extends Entity> {
    Collection<M> selectChildren(O entity);

    O selectParent(M entity);

    boolean update(M child, O parent);
}

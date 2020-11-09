package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;

import java.util.Collection;

public interface OneToManyDao<O extends Entity, M extends Entity> {
    Collection<M> selectMany(long oneId);

    boolean relationExists(long manyId, long oneId);
}

package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;

import java.io.Serializable;
import java.util.Collection;

public abstract class AbstractOneToManyDao<M extends Entity> implements Serializable {
    public abstract Collection<M> selectMany(long oneId);

    public abstract boolean relationExists(long oneId, long manyId);
}

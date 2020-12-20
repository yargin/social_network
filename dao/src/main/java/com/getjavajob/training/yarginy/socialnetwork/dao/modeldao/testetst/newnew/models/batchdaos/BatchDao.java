package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.newnew.models.batchdaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;

import java.util.Collection;

public interface BatchDao<E extends Entity> extends Dao<E> {
    boolean create(Collection<E> entities);

    boolean delete(Collection<E> entities);
}

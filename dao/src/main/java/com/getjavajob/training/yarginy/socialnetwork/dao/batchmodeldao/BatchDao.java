package com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;

import java.util.Collection;

/**
 * provides multiple dao operations onto {@link Entity}
 *
 * @param <E>
 */
public interface BatchDao<E extends Entity> extends Dao<E> {
    /**
     * creates batch of {@link Entity}
     *
     * @param entities to create
     * @return true if creation successful, false if at least one {@link Entity} already exist
     */
    boolean create(Collection<E> entities);

    /**
     * deletes batch of {@link Entity}
     *
     * @param entities to delete
     * @return true if deletion successful, false if not
     */
    boolean delete(Collection<E> entities);

    /**
     *
     */
    boolean update(Collection<E> newEntities, Collection<E> storedEntities);
}

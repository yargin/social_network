package com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;

import java.util.Collection;

public interface BatchDao<E extends Model> extends Dao<E> {
    /**
     * creates batch of {@link Model}
     *
     * @param entities to create
     * @throws IllegalArgumentException if at least one {@link Model} already exist
     */
    void create(Collection<E> entities);

    /**
     * deletes batch of {@link Model}
     *
     * @param entities to delete
     * @throws IllegalArgumentException if deletion was not successful
     */
    void delete(Collection<E> entities);

    /**
     * updates batch of {@link Model} regarding to stored batch of {@link Model}
     *
     * @param newEntities    modified batch of {@link Model}
     * @param storedEntities stored batch of {@link Model}
     * @throws IllegalArgumentException if update was unsuccessful
     */
    void update(Collection<E> newEntities, Collection<E> storedEntities);
}

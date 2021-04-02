package com.getjavajob.training.yarginy.socialnetwork.dao.jpa;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;

import java.util.Collection;

public interface JpaBatchDao<E extends Model> extends JpaDao<E> {
    /**
     * creates batch of {@link Model}
     *
     * @param entities to create
     * @return true if creation successful, false if at least one {@link Model} already exist
     */
    boolean create(Collection<E> entities);

    /**
     * deletes batch of {@link Model}
     *
     * @param entities to delete
     * @return true if deletion successful, false if not
     */
    boolean delete(Collection<E> entities);

    /**
     * updates batch of {@link Model} regarding to stored batch of {@link Model}
     *
     * @param newEntities    modified batch of {@link Model}
     * @param storedEntities stored batch of {@link Model}
     * @return true if update successful, false if not
     */
    boolean update(Collection<E> newEntities, Collection<E> storedEntities);
}

package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;

import java.util.Collection;

/**
 * represents many-to-many relationship between two equal {@link Entity}
 *
 * @param <E> entity type
 */
public interface SelfManyToManyDao<E extends Entity> {
    //todo think about reuse many-to-many
    /**
     * selects all participants related to {@link Entity}
     *
     * @param entity {@link Entity} that other participants related to
     * @return {@link Collection} of participants
     */
    Collection<E> select(E entity);

    /**
     * creates new relation between two {@link Entity}
     *
     * @param first  member of relationship
     * @param second member of relationship
     * @return true if successfully created, false if relationship already exists
     */
    boolean create(E first, E second);

    /**
     * deletes relation between two {@link Entity}
     *
     * @param first  member of relationship
     * @param second member of relationship
     * @return true if successfully deleted, false if relationship doesn't exist
     */
    boolean delete(E first, E second);
}

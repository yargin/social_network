package com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.variousrelated;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.Entity;

import java.util.Collection;

/**
 * represents many-to-many relationship
 *
 * @param <F> first relation participant
 * @param <S> second relation participant
 */
public interface ManyToManyDao<F extends Entity, S extends Entity> {
    /**
     * selects all second participants related to first {@link Entity}
     *
     * @param first {@link Entity} that second participants related to
     * @return {@link Collection} of second participants
     */
    Collection<S> selectByFirst(F first);

    /**
     * selects all first participants related to first {@link Entity}
     *
     * @param second {@link Entity} that first participants related to
     * @return {@link Collection} of first participants
     */
    Collection<F> selectBySecond(S second);

    /**
     * creates new relation between two {@link Entity}
     *
     * @param first  member of relationship
     * @param second member of relationship
     * @return true if successfully created, false if relationship already exists
     */
    boolean create(F first, S second);

    /**
     * deletes relation between two {@link Entity}
     *
     * @param first  member of relationship
     * @param second member of relationship
     * @return true if successfully deleted, false if relationship doesn't exist
     */
    boolean delete(F first, S second);
}

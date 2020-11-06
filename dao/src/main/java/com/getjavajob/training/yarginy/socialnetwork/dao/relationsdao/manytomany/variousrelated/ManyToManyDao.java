package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;

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
    Collection<S> selectByFirst(long firstId);

    /**
     * selects all first participants related to first {@link Entity}
     *
     * @param secondId {@link Entity} that first participants related to
     * @return {@link Collection} of first participants
     */
    Collection<F> selectBySecond(long secondId);

    boolean relationExists(long firstId, long secondId);

    /**
     * creates new relation between two {@link Entity}
     *
     * @return true if successfully created, false if relationship already exists
     */
    boolean create(long firstId, long secondId);

    /**
     * deletes relation between two {@link Entity}
     *
     * @param firstId  member's id of relationship
     * @param secondId member's id of relationship
     * @return true if successfully deleted, false if relationship doesn't exist
     */
    boolean delete(long firstId, long secondId);
}

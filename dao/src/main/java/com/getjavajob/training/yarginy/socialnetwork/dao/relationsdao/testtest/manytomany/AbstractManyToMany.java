package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;

import java.util.Collection;

public abstract class AbstractManyToMany<F extends Entity, S extends Entity> {
    public abstract Collection<S> selectByFirst(long firstId);

    /**
     * selects all first participants related to first {@link Entity}
     *
     * @param secondId id of {@link Entity} that first participants related to
     * @return {@link Collection} of first participants
     */
    public abstract Collection<F> selectBySecond(long secondId);

    /**
     * examines that relation between two {@link Entity}
     *
     * @param firstId  id of member of relationship
     * @param secondId id of member of relationship
     * @return true if exists otherwise false
     */
    public abstract boolean relationExists(long firstId, long secondId);

    /**
     * creates new relation between two {@link Entity}
     *
     * @return true if successfully created, false if relationship already exists
     */
    public abstract boolean create(long firstId, long secondId);

    /**
     * deletes relation between two {@link Entity}
     *
     * @param firstId  member's id of relationship
     * @param secondId member's id of relationship
     * @return true if successfully deleted, false if relationship doesn't exist
     */
    public abstract boolean delete(long firstId, long secondId);
}

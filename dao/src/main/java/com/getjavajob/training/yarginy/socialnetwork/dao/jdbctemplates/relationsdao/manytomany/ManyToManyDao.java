package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.relationsdao.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;

import java.io.Serializable;
import java.util.Collection;

/**
 * represents many-to-many relationship
 *
 * @param <F> first relation participant
 * @param <S> second relation participant
 */
public interface ManyToManyDao<F extends Model, S extends Model> extends Serializable {
    /**
     * selects all second participants related to first {@link Model}
     *
     * @param firstId id of {@link Model} that second participants related to
     * @return {@link Collection} of second participants
     */
    Collection<S> selectByFirst(long firstId);

    /**
     * selects all first participants related to first {@link Model}
     *
     * @param secondId id of {@link Model} that first participants related to
     * @return {@link Collection} of first participants
     */
    Collection<F> selectBySecond(long secondId);

    /**
     * examines that relation between two {@link Model}
     *
     * @param firstId  id of member of relationship
     * @param secondId id of member of relationship
     * @return true if exists otherwise false
     */
    boolean relationExists(long firstId, long secondId);

    /**
     * creates new relation between two {@link Model}
     *
     * @return true if successfully created, false if relationship already exists
     */
    boolean create(long firstId, long secondId);

    /**
     * deletes relation between two {@link Model}
     *
     * @param firstId  member's id of relationship
     * @param secondId member's id of relationship
     * @return true if successfully deleted, false if relationship doesn't exist
     */
    boolean delete(long firstId, long secondId);
}

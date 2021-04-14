package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.selfmanytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;

import java.io.Serializable;
import java.util.Collection;

/**
 * represents many-to-many relationship between two equal {@link Model}
 *
 * @param <E> Model type
 */
public interface SelfManyToManyDao<E extends Model> extends Serializable {
    /**
     * selects all participants related to {@link Model}
     *
     * @param id {@link Model}'s id that other participants related to
     * @return {@link Collection} of participants
     */
    Collection<E> select(long id);

    /**
     * creates new relation between two {@link Model}
     *
     * @param firstId  member of relationship
     * @param secondId member of relationship
     * @return true if successfully created, false if relationship already exists
     */
    boolean create(long firstId, long secondId);

    /**
     * examines that relation between two {@link Model}
     *
     * @param firstId  member of relationship
     * @param secondId member of relationship
     * @return true if exists otherwise false
     */
    boolean relationExists(long firstId, long secondId);

    /**
     * deletes relation between two {@link Model}
     *
     * @param firstId  member of relationship
     * @param secondId member of relationship
     * @return true if successfully deleted, false if relationship doesn't exist
     */
    boolean delete(long firstId, long secondId);
}

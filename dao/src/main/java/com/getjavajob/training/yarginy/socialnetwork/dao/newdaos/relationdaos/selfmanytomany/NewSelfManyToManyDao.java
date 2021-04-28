package com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.selfmanytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;

import java.io.Serializable;
import java.util.Collection;

/**
 * represents many-to-many relationship between two equal {@link Model}
 *
 * @param <E> Model type
 */
public interface NewSelfManyToManyDao<E extends Model> extends Serializable {
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
     * @throws IllegalArgumentException if relationship already exists
     */
    void create(long firstId, long secondId);

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
     * @throws IllegalArgumentException if relationship doesn't exist
     */
    void delete(long firstId, long secondId);
}

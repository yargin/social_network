package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;

import java.io.Serializable;
import java.util.Collection;

/**
 * provides CRUD operations with {@link Entity}
 *
 * @param <E> {@link Entity} inheritor
 */
public interface Dao<E extends Entity> extends Serializable {
    /**
     * retrieves {@link E} specified by id
     *
     * @param id {@link E}'s number
     * @return {@link E} that was found or getNullEntity() if any wasn't found
     */
    E select(long id);

    /**
     * retrieves {@link Entity} from storage according specified data, generally alternate key
     *
     * @param entityToSelect {@link E} with initialised alternate key
     * @return {@link E} that was found or null-entity if wasn't found any
     */
    E select(E entityToSelect);

    /**
     * creates new record storing {@link E} data
     *
     * @param entity {@link E} to store
     * @return if creation was successful, false if already exists
     */
    boolean create(E entity);

    /**
     * modifies record storing {@link E} data, updates only fields that differs from stored
     *
     * @param entity       object containing new values {@link E}
     * @param storedEntity stored {@link E}
     * @return if modification was successful, false if doesn't exists
     */
    boolean update(E entity, E storedEntity);

    /**
     * deletes record storing {@link E} data
     *
     * @param entity {@link E} to delete
     * @return if deletion was successful, false if doesn't exists
     */
    boolean delete(E entity);

    /**
     * selects all entities
     *
     * @return {@link String} representation of data stored in table
     */
    Collection<E> selectAll();

    /**
     * @return {@link Entity} that doesn't exist
     */
    E getNullEntity();
}


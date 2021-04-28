package com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;

import java.io.Serializable;
import java.util.Collection;

public interface Dao<E extends Model> extends Serializable {
    /**
     * retrieves {@link E} specified by id
     *
     * @param id {@link E}'s number
     * @return {@link E} that was found or getNullModel() if any wasn't found
     */
    E select(long id);

    /**
     * retrieves {@link Model} from storage according specified data, generally alternate key
     *
     * @param modelToSelect {@link E} with initialised alternate key
     * @throws IllegalArgumentException if result is not unique
     * @return {@link E} that was found or null-Model if wasn't found any
     */
    E select(E modelToSelect);

    /**
     * creates new record storing {@link E} data
     *
     * @param model {@link E} to store
     * @throws IllegalArgumentException if model already exists
     */
    void create(E model);

    /**
     * modifies record storing {@link E} data, updates only fields that differs from stored
     *
     * @param model       object containing new values {@link E}
     * @throws IllegalArgumentException if model doesn't exist
     * @throws IllegalStateException if model was updated concurrently and performing update will lost those updates
     */
    void update(E model);

    /**
     * deletes record storing {@link E} data
     *
     * @param model {@link E} to delete
     * @throws IllegalArgumentException if model doesn't exist
     * @throws IllegalStateException if model was updated concurrently and performing delete will lost those updates
     */
    void delete(E model);

    /**
     * selects all entities
     *
     * @return {@link String} representation of data stored in table
     */
    Collection<E> selectAll();

    /**
     * @return {@link Model} that doesn't exist
     */
    E getNullModel();
}

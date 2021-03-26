package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;

import java.io.Serializable;
import java.util.Collection;

/**
 * provides CRUD operations with {@link Model}
 *
 * @param <E> {@link Model} inheritor
 */
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
     * @param ModelToSelect {@link E} with initialised alternate key
     * @return {@link E} that was found or null-Model if wasn't found any
     */
    E select(E ModelToSelect);

    /**
     * creates new record storing {@link E} data
     *
     * @param Model {@link E} to store
     * @return if creation was successful, false if already exists
     */
    boolean create(E Model);

    /**
     * modifies record storing {@link E} data, updates only fields that differs from stored
     *
     * @param Model       object containing new values {@link E}
     * @param storedModel stored {@link E}
     * @return if modification was successful, false if doesn't exists
     */
    boolean update(E Model, E storedModel);

    /**
     * deletes record storing {@link E} data
     *
     * @param Model {@link E} to delete
     * @return if deletion was successful, false if doesn't exists
     */
    boolean delete(E Model);

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


package com.getjavajob.training.yarginy.socialnetwork.dao.entities;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.Entity;

/**
 * provides CRUD operations with {@link Entity}
 *
 * @param <E> {@link Entity} inheritor
 */
public interface Dao<E extends Entity> {
    /**
     * retrieves {@link E} specified by id
     *
     * @param id {@link E}'s number
     * @return {@link E} that was found or {@link DaoImpl#getNullEntity()} if any wasn't found
     */
    E select(int id);

    /**
     * retrieves {@link E} specified by email
     *
     * @param identifier {@link E}'s {@link String} identifier
     * @return {@link E} that was found or null-entity if any wasn't found
     */
    E select(String identifier);

    /**
     * creates new record storing {@link E} data
     *
     * @param entity {@link E} to store
     * @return if creation was successful, false if already exists
     */
    boolean create(E entity);

    /**
     * modifies record storing {@link E} data
     *
     * @param entity stored {@link E}
     * @return if modification was successful, false if doesn't exists
     */
    boolean update(E entity);

    /**
     * deletes record storing {@link E} data
     *
     * @param entity {@link E} to delete
     * @return if deletion was successful, false if doesn't exists
     */
    boolean delete(E entity);

    /**
     * selects * from storage. Expected for development usage
     *
     * @return {@link String} representation of data stored in table
     */
    String selectAll();

    /**
     * represents non-existing entity
     *
     * @return representation of non-existing {@link E}
     */
    E getNullEntity();

    /**
     * checks that {@link Entity} is applicable
     *
     * @param entity to check
     */
    void checkEntity(E entity);
}

package com.getjavajob.training.yarginy.socialnetwork.dao.models;

/**
 * provides CRUD operations with {@link Entity}
 *
 * @param <E> {@link Entity} inheritor
 */
public interface EntityDao<E extends Entity> {
    /**
     * retrieves {@link E} specified by id
     *
     * @param id {@link E}'s number
     * @return {@link E} that was found or {@link AbstractEntityDao#getNullEntity()} if any wasn't found
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
     * @return if creation was successful, otherwise false
     */
    boolean create(E entity);

    /**
     * modifies record storing {@link E} data
     *
     * @param entity stored {@link E}
     * @return if modification was successful, otherwise false
     */
    boolean update(E entity);

    /**
     * deletes record storing {@link E} data
     *
     * @param entity {@link E} to delete
     * @return if deletion was successful, otherwise false
     */
    boolean delete(E entity);

    /**
     * represents non-existing entity
     *
     * @return representation of non-existing {@link E}
     */
    E getNullEntity();

    /**
     * selects * from storage. Expected for development usage
     *
     * @return {@link String} representation of data stored in table
     */
    String selectAll();
}

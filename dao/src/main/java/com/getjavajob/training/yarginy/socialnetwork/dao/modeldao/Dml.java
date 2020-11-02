package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Entity;

import java.sql.*;
import java.util.Collection;

/**
 * encapsulates work with SQL-queries, {@link ResultSet}s & {@link Statement}s
 *
 * @param <E> {@link Entity} inheritor to work with
 */
public interface Dml<E extends Entity> {
    PreparedStatement getSelect(Connection connection, E entity, boolean updatable) throws SQLException;


    /**
     * retrieves {@link E} values from {@link ResultSet}'s current cursor position
     *
     * @param resultSet specified {@link ResultSet}
     */
    E selectFromRow(ResultSet resultSet) throws SQLException;

    E selectViewFromRow(ResultSet resultSet) throws SQLException;

    /**
     * retrieves {@link Collection}<{@link E}> from specified {@link ResultSet}
     *
     * @param resultSet specified {@link ResultSet}
     * @return extracted {@link Collection}<{@link E}>
     */
    Collection<E> selectEntities(ResultSet resultSet) throws SQLException;

    /**
     * updates row that given {@link ResultSet}'s cursor currently points to
     *
     * @param resultSet    {@link ResultSet} with positioned cursor
     * @param entity       {@link E}that need to be updated
     * @param storedEntity {@link Entity} that is already stored. Used to detect differing fields that need to add
     * @throws IncorrectDataException if entity's data is incorrect
     */
    void updateRow(ResultSet resultSet, E entity, E storedEntity) throws SQLException;

    /**
     * represents non-existing entity
     *
     * @return representation of non-existing {@link E}
     */
    E getNullEntity();
}

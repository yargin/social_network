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
    /**
     * retrieves {@link PreparedStatement} which execution result will be {@link E} found by it's alternate key. Used
     * for insertion, modification & deletion
     * <br>BE CAREFUL - use only with Try-with-resources or Finally block
     *
     * @param connection {@link Connection} specified connection to make {@link PreparedStatement}
     * @param entity     {@link E} containing data to search by alternate key
     * @return {@link PreparedStatement} that selects {@link E} found by identifier
     */
    PreparedStatement getSelect(Connection connection, E entity) throws SQLException;

    /**
     * retrieves {@link PreparedStatement} which execution result will be {@link E} found by it's alternate key. Used
     * for insertion, modification & deletion
     * <br>BE CAREFUL - use only with Try-with-resources or Finally block
     *
     * @param connection   {@link Connection} specified connection to make {@link PreparedStatement}
     * @param storedEntity {@link E} that is stored
     * @return updatable {@link PreparedStatement} with query that selects {@link E} found by identifier
     */
    PreparedStatement getUpdatableSelect(Connection connection, E storedEntity) throws SQLException;

    /**
     * retrieves {@link E} from {@link ResultSet}'s current cursor position
     *
     * @param resultSet specified {@link ResultSet}
     * @return extracted {@link E}
     */
    E selectFromRow(ResultSet resultSet) throws SQLException;

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

package com.getjavajob.training.yarginy.socialnetwork.dao.entities;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;

import java.sql.*;
import java.util.Collection;

import static java.util.Objects.isNull;

/**
 * encapsulates work with SQL-queries, {@link ResultSet}s & {@link Statement}s
 *
 * @param <E> {@link Entity} inheritor to work with
 */
public abstract class AbstractDml<E extends Entity> {
    /**
     * retrieves {@link PreparedStatement} which execution result will be {@link E} found by email. Used for
     * insertion, modification & deletion
     * <br>BE CAREFUL - use only with Try-with-resources or Finally block
     *
     * @param connection {@link Connection} specified connection to make {@link PreparedStatement}
     * @param id         {@link E}'s id
     * @return {@link PreparedStatement} that selects {@link E} found by id
     */
    public abstract PreparedStatement getSelectStatement(Connection connection, int id) throws SQLException;

    /**
     * retrieves {@link PreparedStatement} which execution result will be {@link E} found by identifier. Used for
     * insertion, modification & deletion
     * <br>BE CAREFUL - use only with Try-with-resources or Finally block
     *
     * @param connection {@link Connection} specified connection to make {@link PreparedStatement}
     * @param identifier {@link E}'s {@link String} identifier
     * @return {@link PreparedStatement} that selects {@link E} found by identifier
     */
    public abstract PreparedStatement getSelectStatement(Connection connection, String identifier) throws SQLException;

    /**
     * retrieves {@link E} from {@link ResultSet}'s current cursor position
     *
     * @param resultSet specified {@link ResultSet}
     * @return extracted {@link E}
     */
    public abstract E selectFromRow(ResultSet resultSet) throws SQLException;

    /**
     * retrieves {@link Collection}<{@link E}> from specified {@link ResultSet}
     *
     * @param resultSet specified {@link ResultSet}
     * @return extracted {@link Collection}<{@link E}>
     */
    public abstract Collection<E> selectEntities(ResultSet resultSet) throws SQLException;

    /**
     * updates row that given {@link ResultSet}'s cursor currently points to
     *
     * @param resultSet {@link ResultSet} with positioned cursor
     * @param entity    {@link E}that need to be updated
     * @throws IncorrectDataException if entity's data is incorrect
     */
    public abstract void updateRow(ResultSet resultSet, E entity) throws SQLException;

    /**
     * provides query that will select all data from Entity's table
     *
     * @return query that selects all data
     */
    public abstract String getSelectAllQuery();

    /**
     * represents non-existing entity
     *
     * @return representation of non-existing {@link E}
     */
    public abstract E getNullEntity();

    protected String checkedString(String stringToCheck) {
        if (isNull(stringToCheck) || "".equals(stringToCheck)) {
            throw new IncorrectDataException("can't be null or empty");
        }
        return stringToCheck;
    }
}

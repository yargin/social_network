package com.getjavajob.training.yarginy.socialnetwork.dao.entities;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.Entity;
import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;

import java.sql.*;
import java.util.Collection;

/**
 * encapsulates work with SQL-queries, {@link ResultSet}s & {@link Statement}s
 *
 * @param <E> {@link Entity} inheritor to work with
 */
public abstract class AbstractDml<E extends Entity> {
    /**
     * retrieves {@link PreparedStatement} which execution result will be {@link E} found by id number
     * <br>BE CAREFUL - use only with Try-with-resources or Finally block
     *
     * @param connection {@link Connection} specified connection to make {@link PreparedStatement}
     * @param id         {@link E}'s id
     * @return {@link PreparedStatement} that selects {@link E} found by id
     */
    public PreparedStatement getSelectStatement(Connection connection, long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(getSelectById());
        statement.setLong(1, id);
        return statement;
    }

    /**
     * query to select by id
     *
     * @return {@link String} representation of select by id number query
     */
    protected abstract String getSelectById();

    /**
     * retrieves {@link PreparedStatement} which execution result will be {@link E} found by identifier. Used for
     * insertion, modification & deletion
     * <br>BE CAREFUL - use only with Try-with-resources or Finally block
     *
     * @param connection {@link Connection} specified connection to make {@link PreparedStatement}
     * @param identifier {@link E}'s {@link String} identifier
     * @return {@link PreparedStatement} that selects {@link E} found by identifier
     */
    public PreparedStatement getSelectStatement(Connection connection, String identifier) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(getSelectByIdentifier());
        statement.setString(1, identifier);
        return statement;
    }

    /**
     * query to select by {@link String} identifier
     *
     * @return {@link String} representation of select by string identifier number query
     */
    protected abstract String getSelectByIdentifier();

    /**
     * retrieves {@link PreparedStatement} which execution result will be {@link E} found by identifier. Used for
     * insertion, modification & deletion
     * <br>BE CAREFUL - use only with Try-with-resources or Finally block
     *
     * @param connection {@link Connection} specified connection to make {@link PreparedStatement}
     * @param identifier {@link E}'s {@link String} identifier
     * @return updatable {@link PreparedStatement} with query that selects {@link E} found by identifier
     */
    public PreparedStatement getUpdatableSelectStatement(Connection connection, String identifier) throws
            SQLException {
        PreparedStatement statement = connection.prepareStatement(getUpdatableSelect(), ResultSet.
                TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, identifier);
        return statement;
    }

    /**
     * query to select {@link Entity}s for update
     *
     * @return {@link String} representation of updatable select query
     */
    protected abstract String getUpdatableSelect();

    /**
     * provides query that will select all data from Entity's table
     *
     * @return query that selects all data
     */
    public PreparedStatement getSelectAllStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(getSelectAll());
    }

    /**
     * query to select all {@link Entity}s
     *
     * @return {@link String} representation select all query
     */
    protected abstract String getSelectAll();

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
     * represents non-existing entity
     *
     * @return representation of non-existing {@link E}
     */
    public abstract E getNullEntity();
}

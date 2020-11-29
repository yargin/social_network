package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao;

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
     * creates {@link PreparedStatement} used by {@link ResultSet} for CRUD operations
     * select all {@link Entity} fields for CRUD
     *
     * @param connection {@link Connection}
     * @param entity to select
     * @param updatable flag telling if {@link PreparedStatement} will be used for update
     * @return created {@link PreparedStatement}
     * @throws SQLException
     */
    PreparedStatement getSelect(Connection connection, E entity, boolean updatable) throws SQLException;

    /**
     * retrieves fields values from {@link ResultSet} into new {@link Entity} instance
     *
     * @param resultSet set of data to retrieve from
     * @return retrieved {@link Entity}
     * @throws SQLException
     */
    E retrieveFromRow(ResultSet resultSet) throws SQLException;

    /**
     * retrieves small portion(usually needed for toString() and equals())  of fields values from {@link ResultSet}
     * into new {@link Entity} instance
     *
     * @param resultSet set of data to retrieve from
     * @return retrieved {@link Entity}
     * @throws SQLException
     */
    E retrieveViewFromRow(ResultSet resultSet) throws SQLException;

    /**
     * retrieves {@link Collection} of {@link Entity}s from set of data
     *
     * @param resultSet set of data to retrieve from
     * @return new {@link Collection} of {@link Entity}s
     * @throws SQLException
     */
    Collection<E> retrieveEntities(ResultSet resultSet) throws SQLException;

    /**
     * updates fields in storage. Fields to update are chosen regarding on comparing stored {@link Entity} and
     * specified one
     *
     * @param resultSet view of stored data
     * @param entity {@link Entity} with updated values
     * @param storedEntity stored {@link Entity}
     * @throws SQLException
     */
    void updateRow(ResultSet resultSet, E entity, E storedEntity) throws SQLException;

    E getNullEntity();
}

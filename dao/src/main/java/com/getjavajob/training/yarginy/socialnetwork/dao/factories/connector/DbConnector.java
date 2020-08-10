package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * provides connection to database
 */
public interface DbConnector {
    /**
     * it is not specified that {@link Connection} will be closed in implementation.
     * <br>BE CAREFUL - use only with Try-with-resources or Finally block
     *
     * @return {@link Connection} to database
     * @throws IllegalStateException if it's not possible to connect to database
     */
    Connection getConnection() throws SQLException;

    int getCapacity();
}

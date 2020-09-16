package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * provides {@link Connection}s to database bounded to current thread
 */
public interface ConnectionPool {
    int getCapacity();

    /**
     * provides {@link Connection} to database. If current tread didn't start {@link Transaction}
     * commits updates on close. Otherwise commits updates on {@link Transaction#commit()}
     *
     * @return {@link Connection} to database
     * @throws SQLException if db isn't available
     */
    Connection getConnection() throws SQLException;
}

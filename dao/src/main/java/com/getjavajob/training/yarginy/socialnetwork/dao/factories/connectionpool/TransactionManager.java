package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool;

import java.sql.Connection;

public final class TransactionManager {
    private static ConnectionPoolImpl connectionPool;

    private TransactionManager() {
    }

    static void initConnectionPool(ConnectionPoolImpl connectionPool) {
        TransactionManager.connectionPool = connectionPool;
    }

    /**
     * starts transaction bounded to current thread by bounding connection to it
     * All connections retrieved from {@link ConnectionPool} are transactional -
     * no any commit/rollback when are closed
     *
     * @throws IllegalStateException if couldn't connect to database
     */
    public static synchronized Transaction getTransaction() {
        ConnectionProxy connectionProxy =  connectionPool.getConnectionProxy();
        return new TransactionImpl(connectionProxy);
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool;

public final class TransactionManagerImpl {
    private static ConnectionPoolImpl connectionPool;

    private TransactionManagerImpl() {
    }

    static void initConnectionPool(ConnectionPoolImpl connectionPool) {
        TransactionManagerImpl.connectionPool = connectionPool;
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

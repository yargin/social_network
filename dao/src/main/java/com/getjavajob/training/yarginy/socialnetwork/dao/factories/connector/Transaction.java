package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector;

import java.sql.Savepoint;

public interface Transaction {
    /**
     * starts transaction bounded to current thread by bounding connection to it
     * All connections retrieved from {@link ConnectionPool} are transactional -
     * no any commit/rollback when are closed
     *
     * @throws IllegalStateException if couldn't connect to database
     */
    void begin();

    /**
     * ends transaction bounded to current thread
     *
     * @throws IllegalStateException if couldn't close bounded connection
     */
    void end();

    /**
     * applies changes. Doesn't close connection or transaction
     */
    void commit();

    /**
     * creates {@link Savepoint}
     */
    Savepoint setSavepoint();

    /**
     * discards changes. Doesn't close connection or transaction
     */
    void rollback();

    /**
     * discards changes after specified {@link Savepoint}. Doesn't close connection or transaction
     */
    void rollback(Savepoint savepoint);
}

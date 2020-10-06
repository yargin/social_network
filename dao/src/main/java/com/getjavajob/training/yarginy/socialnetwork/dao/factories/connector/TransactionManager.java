package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector;

public interface TransactionManager {
    /**
     * starts transaction bounded to current thread by bounding connection to it
     * All connections retrieved from {@link ConnectionPool} are transactional -
     * no any commit/rollback when are closed
     *
     * @throws IllegalStateException if couldn't connect to database
     */
    Transaction getTransaction();
}

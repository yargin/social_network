package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.*;

public final class TransactionManager {
    private TransactionManager() {
    }

    /**
     * starts transaction bounded to current thread by bounding connection to it
     * All connections retrieved from {@link ConnectionPool} are transactional -
     * no any commit/rollback when are closed
     *
     * @throws IllegalStateException if couldn't connect to database
     */
    public static synchronized Transaction getTransaction() {
        return TransactionManagerImpl.getTransaction();
    }
}

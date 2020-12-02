package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool;

import java.io.Serializable;
import java.sql.Savepoint;

public interface Transaction extends AutoCloseable, Serializable {
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

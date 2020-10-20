package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class TransactionImpl implements Transaction {
    private final ConnectionProxy connectionProxy;

    public TransactionImpl(ConnectionProxy connectionProxy) {
        this.connectionProxy = connectionProxy;
        connectionProxy.setTransactional(true);
    }

    @Override
    public void commit() {
        try {
            connectionProxy.commit();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Savepoint setSavepoint() {
        try {
            return connectionProxy.setSavepoint();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void rollback() {
        try {
            connectionProxy.rollback();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void rollback(Savepoint savepoint) {
        try {
            connectionProxy.rollback(savepoint);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws Exception {
        connectionProxy.setTransactional(false);
        connectionProxy.close();
    }
}

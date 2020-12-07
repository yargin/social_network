package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool;

import com.getjavajob.training.yarginy.socialnetwork.dao.facades.TransactionManager;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

/**
 * crams between {@link DataSource} and application to allow Transaction manager hold connection during transaction
 */
public class DataSourceProxy implements DataSource, Serializable {
    private final ThreadLocal<ConnectionProxy> localConnection = new ThreadLocal<>();
    private final DataSource dataSource;

    public DataSourceProxy(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void closeConnection() {
        ConnectionProxy connectionProxy = localConnection.get();
        if (connectionProxy.isTransactional()) {
            return;
        }
        try {
            connectionProxy.closeActually();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        localConnection.remove();
    }

    ConnectionProxy getConnectionProxy() {
        ConnectionProxy connectionProxy = localConnection.get();
        if (isNull(connectionProxy)) {
            try {
                connectionProxy = new ConnectionProxy(dataSource.getConnection(), this);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            localConnection.set(connectionProxy);
        }
        return connectionProxy;
    }

    @Override
    public Connection getConnection() {
        return getConnectionProxy();
    }

    @Override
    public Connection getConnection(String username, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PrintWriter getLogWriter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLogWriter(PrintWriter out) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLoginTimeout() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLoginTimeout(int seconds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Logger getParentLogger() {
        throw new UnsupportedOperationException();
    }

    public TransactionManager getTransactionManager() {
        return new InnerTransactionManager();
    }

    private class InnerTransactionManager implements TransactionManager {
        public Transaction getTransaction() {
            return new TransactionImpl(getConnectionProxy());
        }
    }
}

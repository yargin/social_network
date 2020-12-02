package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

public abstract class AbstractDataSource implements DataSource, Serializable {
    private final ThreadLocal<ConnectionProxy> localConnection = new ThreadLocal<>();

    protected AbstractDataSource() {
        TransactionManagerImpl.init(this);
    }

    protected abstract void closeConnectionProxy(ConnectionProxy connectionProxy);

    abstract ConnectionProxy retrieveConcreteConnectionProxy();

    public void closeConnection() {
        ConnectionProxy connectionProxy = localConnection.get();
        if (connectionProxy.isTransactional()) {
            return;
        }
        closeConnectionProxy(connectionProxy);
        localConnection.remove();
    }

    ConnectionProxy getConnectionProxy() {
        ConnectionProxy connectionProxy = localConnection.get();
        if (isNull(connectionProxy)) {
            connectionProxy = retrieveConcreteConnectionProxy();
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
}

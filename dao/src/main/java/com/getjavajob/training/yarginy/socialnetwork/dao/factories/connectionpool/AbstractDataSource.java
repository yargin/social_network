package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

public abstract class AbstractDataSource implements DataSource {
    private final ThreadLocal<ConnectionProxy> localConnection = new ThreadLocal<>();

    public AbstractDataSource() {
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
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException();
    }
}

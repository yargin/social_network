package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

public final class ConnectionPoolImpl implements DataSource {
    private static ConnectionPoolImpl connectionPoolImpl;
    private final BlockingQueue<ConnectionProxy> connectionsQueue;
    private final ThreadLocal<ConnectionProxy> threadConnection;

    private ConnectionPoolImpl(String propertiesFile, int capacity) {
        Properties properties = new Properties();
        try (InputStream inputStream = ConnectionPoolImpl.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        connectionsQueue = new ArrayBlockingQueue<>(capacity);
        threadConnection = new ThreadLocal<>();
        boolean put;
        do {
            try {
                put = connectionsQueue.offer(new ConnectionProxy(DriverManager.getConnection(properties.getProperty(
                        "url"), properties),this));
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        } while (put);
        TransactionManagerImpl.initConnectionPool(this);
    }

    public static ConnectionPoolImpl getConnectionPool(String propertiesFile, int capacity) {
        if (isNull(connectionPoolImpl)) {
            connectionPoolImpl = new ConnectionPoolImpl(propertiesFile, capacity);
        }
        return connectionPoolImpl;
    }

    public void closeConnectionProxy() {
        ConnectionProxy connectionProxy = threadConnection.get();
        if (connectionProxy.isTransactional()) {
            return;
        }
        try {
            threadConnection.remove();
            connectionsQueue.put(connectionProxy);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    ConnectionProxy getConnectionProxy() {
        ConnectionProxy connection = threadConnection.get();
        if (!isNull(connection)) {
            return connection;
        }
        try {
            connection = connectionsQueue.take();
            threadConnection.set(connection);
            return connection;
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Connection getConnection() {
        return getConnectionProxy();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("not supported");
    }
}

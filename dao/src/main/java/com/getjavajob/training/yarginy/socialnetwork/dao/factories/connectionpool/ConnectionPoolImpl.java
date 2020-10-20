package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.util.Objects.isNull;

public class ConnectionPoolImpl implements ConnectionPool {
    private static ConnectionPoolImpl connectionPoolImpl;
    private final BlockingQueue<ConnectionProxy> connectionsQueue;
    private final ThreadLocal<ConnectionProxy> threadConnection;
    private final int capacity;

    private ConnectionPoolImpl(String propertiesFile, int capacity) {
        Properties properties = new Properties();
        try (InputStream inputStream = ConnectionPoolImpl.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        this.capacity = capacity;
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

    public int getCapacity() {
        return capacity;
    }
}

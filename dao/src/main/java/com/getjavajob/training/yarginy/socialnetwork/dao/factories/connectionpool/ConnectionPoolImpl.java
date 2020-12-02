package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.util.Objects.isNull;

public final class ConnectionPoolImpl extends AbstractDataSource {
    private static ConnectionPoolImpl connectionPoolImpl;
    private final BlockingQueue<ConnectionProxy> connectionsQueue;

    private ConnectionPoolImpl(String propertiesFile, int capacity) {
        Properties properties = new Properties();
        try (InputStream inputStream = ConnectionPoolImpl.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        connectionsQueue = new ArrayBlockingQueue<>(capacity);
        boolean put;
        do {
            try {
                put = connectionsQueue.offer(new ConnectionProxy(DriverManager.getConnection(properties.getProperty(
                        "url"), properties),this));
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        } while (put);
    }

    public static ConnectionPoolImpl getConnectionPool(String propertiesFile, int capacity) {
        if (isNull(connectionPoolImpl)) {
            connectionPoolImpl = new ConnectionPoolImpl(propertiesFile, capacity);
        }
        return connectionPoolImpl;
    }

    @Override
    public void closeConnectionProxy(ConnectionProxy connectionProxy) {
        try {
            connectionsQueue.put(connectionProxy);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    ConnectionProxy retrieveConcreteConnectionProxy() {
        try {
            return connectionsQueue.take();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}

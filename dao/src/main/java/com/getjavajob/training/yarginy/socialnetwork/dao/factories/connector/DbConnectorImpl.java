package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class DbConnectorImpl implements DbConnector {
    private static DbConnector singleDbConnector;
    private final Properties properties = new Properties();
    private final BlockingQueue<ConnectionProxy> connections;
    private final Semaphore semaphore;
    private final int capacity;

    private DbConnectorImpl(String propertiesFile, int capacity) {
        try (InputStream inputStream = DbConnectorImpl.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        this.capacity = capacity;
        connections = new ArrayBlockingQueue<>(capacity);
        semaphore = new Semaphore(capacity);
    }

    public static DbConnector getDbConnector(String propertiesFile, int capacity) {
//        if (isNull(singleDbConnector)) {
//            singleDbConnector = new DbConnectorImpl(propertiesFile, capacity);
//            return singleDbConnector;
//        } else {
//            throw new UnsupportedOperationException("only one instance allowed");
//        }
        return new DbConnectorImpl(propertiesFile, capacity);
    }

    public void closeConnection(ConnectionProxy connectionProxy) throws SQLException {
        //if no Connection's consumer is waiting for connection
        if (semaphore.getQueueLength() == 0) {
            connectionProxy.closeActually();
            //if some consumer waiting for connection
        } else {
            try {
                connections.put(connectionProxy);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e.getMessage());
            }
        }
        semaphore.release();
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            semaphore.acquire();
            if (connections.isEmpty()) {
                return new ConnectionProxy(DriverManager.getConnection(properties.getProperty("url"), properties), this);
            }
            //blocks until at least one connection becomes free
            return connections.take();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public int getCapacity() {
        return capacity;
    }
}

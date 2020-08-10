package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.pool3;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnectorImpl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.isNull;

public class DbConnectorImpl3 implements DbConnector3 {
    private static DbConnector3 singleDbConnector;
    private final Properties properties = new Properties();
    private final BlockingQueue<ConnectionProxy3> usedConnections;
    private final BlockingQueue<ConnectionProxy3> reusableConnections;
    private final int capacity;
    private final AtomicInteger waiting = new AtomicInteger(0);

    private DbConnectorImpl3(String propertiesFile, int capacity) {
        try (InputStream inputStream = DbConnectorImpl.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        this.capacity = capacity;
        usedConnections = new ArrayBlockingQueue<>(capacity);
        reusableConnections = new ArrayBlockingQueue<>(capacity);
    }

    public static DbConnector3 getDbConnector(String propertiesFile, int capacity) {
        if (isNull(singleDbConnector)) {
            singleDbConnector = new DbConnectorImpl3(propertiesFile, capacity);
            return singleDbConnector;
        } else {
            throw new UnsupportedOperationException("only one instance allowed");
        }
    }

    public void closeConnection(ConnectionProxy3 connectionProxy) throws SQLException {
        //if no Connection's consumer is waiting for connection
        try {
            if (waiting.get() == 0) {
                usedConnections.take().closeActually();
                //if some consumer waiting for connection
            } else {
                reusableConnections.put(usedConnections.take());
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            ConnectionProxy3 connection;
            if (usedConnections.remainingCapacity() > 0) {
                connection = new ConnectionProxy3(DriverManager.getConnection(properties.getProperty(
                        "url"), properties), this);
                usedConnections.put(connection);
                return connection;
            }
            waiting.getAndIncrement();
            synchronized (this) {
                connection = reusableConnections.take();
                waiting.getAndDecrement();
                return connection;
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public int getCapacity() {
        return capacity;
    }
}

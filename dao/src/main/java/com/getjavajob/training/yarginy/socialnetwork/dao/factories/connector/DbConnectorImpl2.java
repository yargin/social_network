package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DbConnectorImpl2 implements DbConnector {
    private final Properties properties = new Properties();
    private final BlockingQueue<ConnectionConsumer> connectionConsumers;
    private volatile int counter;

    public DbConnectorImpl2(String propertiesFile, int capacity) {
        try (InputStream inputStream = DbConnectorImpl.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        connectionConsumers = new ArrayBlockingQueue<>(capacity);
    }

    public synchronized void closeConnection(ConnectionProxy connectionProxy) throws SQLException {
        //if no Connection's consumer is waiting for connection
        if (!connectionConsumers.isEmpty()) {
            connectionProxy.closeReally();
        } else {
            try {
                connectionConsumers.take().takeConnection(connectionProxy);
                connectionProxy.setBeingUsed();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        try {
            if (counter > 0) {
//                return new ConnectionProxy(DriverManager.getConnection(properties.getProperty("url"), properties), this);
            } else {
                ConnectionConsumer connectionConsumer = new ConnectionConsumer();
                connectionConsumers.put(connectionConsumer);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class ConnectionConsumer {
        Connection takeConnection(ConnectionProxy connectionProxy) {
            return connectionProxy;
        }
    }
}

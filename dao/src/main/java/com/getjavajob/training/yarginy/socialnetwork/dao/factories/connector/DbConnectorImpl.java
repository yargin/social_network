package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static java.util.Objects.isNull;

public class DbConnectorImpl implements DbConnector {
    private static DbConnector singleDbConnector;
    private final Properties properties = new Properties();
    private final ConnectionProxy[] connections;
    private volatile boolean waitingForConnection;
    private final Object consumersMutex = new Object();
    private final Object proxyMutex = new Object();

    private DbConnectorImpl(String propertiesFile, int capacity) {
        try (InputStream inputStream = DbConnectorImpl.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        connections = new ConnectionProxy[capacity];
    }

    public static DbConnector getDbConnector(String propertiesFile, int capacity) {
//        if (isNull(singleDbConnector)) {
//            singleDbConnector = new DbConnectorImpl(propertiesFile, capacity);
//        }
//        throw new UnsupportedOperationException("only one instance available");

        return new DbConnectorImpl(propertiesFile, capacity);
    }

    public void closeConnection(ConnectionProxy connectionProxy) throws SQLException {
        //if no Connection's consumer is waiting for connection
        synchronized (proxyMutex) {
            if (!waitingForConnection) {
                connectionProxy.closeActually();
            } else {
                connectionProxy.setBeingUsed(false);
            }
        }
    }

    @Override
    public int getCapacity() {
        return connections.length;
    }

    @Override
    public Connection getConnection() throws SQLException {
        synchronized (consumersMutex) {
            waitingForConnection = true;
            int capacity = connections.length;
            while (true) {
                for (int i = 0; i < capacity; i++) {
                    //create new ConnectionProxy if currently iterated not initialised yet or actually closed
                    if (isNull(connections[i]) || connections[i].isClosed()) {
                        connections[i] = new ConnectionProxy(DriverManager.getConnection(properties.getProperty("url"),
                                properties), this);
                        connections[i].setAutoCommit(false);
                        waitingForConnection = false;
                        connections[i].setBeingUsed(true);
                        return connections[i];
                        //reuse connected ConnectionProxy
                    } else if (!connections[i].isBeingUsed()) {
                        waitingForConnection = false;
                        connections[i].setBeingUsed(true);
                        return connections[i];
                    }
                }
            }
        }
    }
}

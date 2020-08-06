package com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static java.util.Objects.isNull;

public class DbConnectorImpl implements DbConnector {
    private final Properties properties = new Properties();
    private final ConnectionProxy[] connections;
    private volatile boolean waitingForConnection;

    public DbConnectorImpl(String propertiesFile, int capacity) {
        try (InputStream inputStream = DbConnectorImpl.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        connections = new ConnectionProxy[capacity];
    }

    public synchronized void closeConnection(ConnectionProxy connectionProxy) throws SQLException {
        //if no Connection's consumer is waiting for connection
        if (!waitingForConnection) {
            connectionProxy.closeReally();
        }
    }

    @Override
    public synchronized Connection getConnection() throws SQLException {
        waitingForConnection = true;
        int capacity = connections.length;
        while (true) {
            for (int i = 0; i < capacity; i++) {
                //create new ConnectionProxy if currently iterated not initialised yet or really closed
                if (isNull(connections[i]) || connections[i].isClosed()) {
                    connections[i] = new ConnectionProxy(DriverManager.getConnection(properties.getProperty("url"),
                            properties), this);
                    connections[i].setAutoCommit(false);
                    waitingForConnection = false;
                    return connections[i];
                    //reuse connected ConnectionProxy
                } else if (!connections[i].isBeingUsed()) {
                    waitingForConnection = false;
                    connections[i].setBeingUsed();
                    return connections[i];
                }
            }
        }
    }
}

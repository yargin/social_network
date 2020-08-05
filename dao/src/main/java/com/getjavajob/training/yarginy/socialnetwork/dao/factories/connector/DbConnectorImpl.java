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
    private final Connection[] connections;

    public DbConnectorImpl(String propertiesFile, int capacity) {
        try (InputStream inputStream = DbConnectorImpl.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        connections = new Connection[capacity];
    }

    public synchronized Connection getConnection() throws SQLException {
        int capacity = connections.length;
        while (true) {
            for (int i = 0; i < capacity; i++) {
                if (isNull(connections[i]) || connections[i].isClosed()) {
                    connections[i] = DriverManager.getConnection(properties.getProperty("url"), properties);
                    connections[i].setAutoCommit(false);
                    return connections[i];
                }
            }
        }
    }
}

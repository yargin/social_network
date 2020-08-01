package com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.implementations;

import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.AbstractConnectionFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DbConnector;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectorImpl implements DbConnector {
    private final String propertiesFile;

    public DbConnectorImpl(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        try (InputStream inputStream = AbstractConnectionFactory.class.getClassLoader().getResourceAsStream(
                propertiesFile)) {
            properties.load(inputStream);
            Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
            connection.setAutoCommit(false);
            return connection;
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}

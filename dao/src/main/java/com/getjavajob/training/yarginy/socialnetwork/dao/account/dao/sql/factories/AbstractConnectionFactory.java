package com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class AbstractConnectionFactory {
    public abstract AbstractDMLQueriesFactory getDMLQueriesFactory();

    public Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        try (InputStream inputStream = AbstractConnectionFactory.class.getClassLoader().getResourceAsStream(
                getPropertiesFile())) {
            properties.load(inputStream);
            Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
            connection.setAutoCommit(false);
            return connection;
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    protected abstract String getPropertiesFile();
}

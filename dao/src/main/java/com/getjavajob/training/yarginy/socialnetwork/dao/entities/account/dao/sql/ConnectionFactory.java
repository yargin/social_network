package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao.sql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final String PROPERTIES = "mysql/MySQLConnection.properties";

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        try (InputStream inputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream(PROPERTIES)) {
            properties.load(inputStream);
            Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
                connection.setAutoCommit(false);
                return connection;
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}

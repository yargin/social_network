package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.sql;

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
            return DriverManager.getConnection(properties.getProperty("url"), properties);
        } catch (IOException e) {
            throw new IllegalStateException(e.getCause());
        }
    }
}

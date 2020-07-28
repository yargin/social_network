package com.getjavajob.training.yarginy.socialnetwork.dao.dbutils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final String PROPERTIES = "MySQLConnection.properties";
    private static final String ACCOUNT_META_DATA = "MySQLDatabaseMetaData";
    String TABLE = "Accounts";
    String ID = "Id";
    String NAME = "Name";
    String SURNAME = "Surname";
    String PATRONYMIC = "Patronymic";
    String SEX = "Sex";
    String BIRTH_DATE = "Birth_date";
    String HOME_PHONE = "Home_phone";
    String WORK_PHONE = "Work_phone";
    String HOME_ADDRESS = "Home_address";
    String WORK_ADDRESS = "Work_address";
    String EMAIL = "Email";
    String ADDITIONAL_EMAIL = "Additional_email";
    String ICQ = "Icq";
    String SKYPE = "Skype";
    String CITY = "City";
    String COUNTRY = "Country";

    public static Connection getConnection() {
        Properties properties = new Properties();
        try (InputStream inputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream(PROPERTIES)) {
            properties.load(inputStream);
            return DriverManager.getConnection(properties.getProperty("url"), properties);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}

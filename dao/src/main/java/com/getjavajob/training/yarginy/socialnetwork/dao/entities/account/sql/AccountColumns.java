package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AccountColumns {
    private static final String ACCOUNT_META_DATA = "MySQLDatabaseMetaData";
    static Properties properties = new Properties();
    static {
        try (InputStream inputStream = AccountColumns.class.getClassLoader().getResourceAsStream(ACCOUNT_META_DATA)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e.getCause());
        }
    }
    public static final String TABLE = properties.getProperty("accounts.table");
    public static final String ID = properties.getProperty("accounts.id");
    public static final String NAME = properties.getProperty("accounts.name");
    public static final String SURNAME = properties.getProperty("accounts.surname");
    public static final String PATRONYMIC = properties.getProperty("accounts.patronymic");
    public static final String SEX = properties.getProperty("accounts.sex");
    public static final String BIRTH_DATE = properties.getProperty("accounts.birth_date");
    public static final String PHONE = properties.getProperty("accounts.phone");
    public static final String WORK_PHONE = properties.getProperty("accounts.work_phone");
    public static final String HOME_ADDRESS = properties.getProperty("accounts.home_address");
    public static final String WORK_ADDRESS = properties.getProperty("accounts.work_address");
    public static final String EMAIL = properties.getProperty("accounts.email");
    public static final String ADDITIONAL_EMAIL = properties.getProperty("accounts.additional_email");
    public static final String ICQ = properties.getProperty("accounts.icq");
    public static final String SKYPE = properties.getProperty("accounts.skype");
    public static final String CITY = properties.getProperty("accounts.city");
    public static final String COUNTRY = properties.getProperty("accounts.country");
}

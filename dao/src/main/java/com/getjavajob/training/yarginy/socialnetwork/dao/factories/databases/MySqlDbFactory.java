package com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;

public class MySqlDbFactory extends AbstractDbFactory {
    private static final String DB_CONNECTION_FILE = "connections/MySQLConnection.properties";
    private static final String DML_QUERIES_FILE = "./src/main/resources/scripts/MySQL/";

    @Override
    protected String getConnectionFile() {
        return DB_CONNECTION_FILE;
    }

    @Override
    protected String getDmlDirectory() {
        return DML_QUERIES_FILE;
    }
}

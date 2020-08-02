package com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;

public class H2DbFactory extends AbstractDbFactory {
    private static final String DB_CONNECTION_FILE = "connections/H2Connection.properties";
    private static final String DML_QUERIES_FILE = "./src/main/resources/scripts/H2/";

    @Override
    protected String getConnectionFile() {
        return DB_CONNECTION_FILE;
    }

    @Override
    protected String getDmlDirectory() {
        return DML_QUERIES_FILE;
    }
}

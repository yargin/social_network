package com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.databases;

public class H2Factory extends AbstractDbFactory {
    private static final String DB_CONNECTION_FILE = "connections/H2Connection.properties";
    private static final String DML_QUERIES_FILE = "./src/main/resources/scripts/H2/";

    @Override
    protected String getConnectionPropertiesFile() {
        return DB_CONNECTION_FILE;
    }

    @Override
    protected String getDmlQueriesFile() {
        return DML_QUERIES_FILE;
    }
}

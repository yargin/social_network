package com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory;

public class MySqlDbFactory extends AbstractDbFactory {
    private static final String DB_CONNECTION_FILE = "connections/MySQLConnection.properties";
    private static final String SCRIPTS_DIR = "./src/main/resources/scripts/MySQL/";

    @Override
    protected String getConnectionFile() {
        return DB_CONNECTION_FILE;
    }

    @Override
    protected String getScriptDirectory() {
        return SCRIPTS_DIR;
    }

    @Override
    protected String getStartingScript() {
        throw new UnsupportedOperationException("not specified");
    }

    @Override
    protected boolean runScriptOnStart() {
        return false;
    }
}

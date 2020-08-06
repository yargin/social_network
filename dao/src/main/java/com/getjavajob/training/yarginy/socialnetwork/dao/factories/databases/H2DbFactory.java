package com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.CommonDbFactory;

public class H2DbFactory extends CommonDbFactory {
    private static final String DB_CONNECTION_FILE = "connections/H2Connection.properties";
    private static final String SCRIPTS_DIR = "./src/main/resources/scripts/H2/";
    private static final String CREATION_SCRIPT = "run_creation.sql";
    private static final int CONNECTIONS = 3;

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
        return CREATION_SCRIPT;
    }

    @Override
    protected boolean runScriptOnStart() {
        return true;
    }

    @Override
    protected int getConnectionsCapacity() {
        return CONNECTIONS;
    }
}

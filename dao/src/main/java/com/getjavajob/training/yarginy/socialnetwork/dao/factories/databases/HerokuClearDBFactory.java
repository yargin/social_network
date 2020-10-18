package com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.CommonDbFactory;

public class HerokuClearDBFactory extends CommonDbFactory {
    private static final String DB_CONNECTION_FILE = "connections/ClearDBConnection.properties";
    private static final String SCRIPTS_DIR = "./src/main/resources/scripts/MySQL/";
    private static final String CREATION_SCRIPT = "run_creation.sql";
    private static final int CONNECTIONS = 10;

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
        return false;
    }

    @Override
    protected int getConnectionsCapacity() {
        return CONNECTIONS;
    }
}

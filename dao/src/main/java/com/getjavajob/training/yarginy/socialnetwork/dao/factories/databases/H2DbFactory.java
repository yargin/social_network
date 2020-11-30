package com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.CommonDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPoolImpl;

import javax.sql.DataSource;

public class H2DbFactory extends CommonDbFactory {
    private static final String DB_CONNECTION_FILE = "connections/H2Connection.properties";
    private static final String SCRIPTS_DIR = "./src/main/resources/scripts/H2/";
    private static final String CREATION_SCRIPT = "run_creation.sql";
    private static final int CONNECTIONS = 2;
    private static final DataSource DATA_SOURCE = init();

    public H2DbFactory() {
        super(DATA_SOURCE);
    }

    private static DataSource init() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        return ConnectionPoolImpl.getConnectionPool(DB_CONNECTION_FILE, CONNECTIONS);
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
}

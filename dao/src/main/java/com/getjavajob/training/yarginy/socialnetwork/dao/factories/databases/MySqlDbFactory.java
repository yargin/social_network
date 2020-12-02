package com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.CommonDbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPoolImpl;

import javax.sql.DataSource;

public class MySqlDbFactory extends CommonDbFactory {
    private static final String DB_CONNECTION_FILE = "connections/MySQLConnection.properties";
    private static final int CONNECTIONS = 4;

    private static final String SCRIPTS_DIR = "./src/main/resources/scripts/MySQL/";
    private static final DataSource DATA_SOURCE = init();

    public MySqlDbFactory() {
        super(DATA_SOURCE);
    }

    private static DataSource init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
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
        throw new UnsupportedOperationException("not specified");
    }

    @Override
    protected boolean runScriptOnStart() {
        return false;
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.factories.databases;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.CommonDbFactory;

public class JawsDBFactory extends CommonDbFactory {
    private static final String DB_CONNECTION_FILE = "connections/JawsDBConnection";
    private static final String SCRIPTS_DIR = "./src/main/resources/scripts/MySQL/";
    private static final int CONNECTIONS = 4;

    public JawsDBFactory() {
        //register driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

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

    @Override
    protected int getConnectionsCapacity() {
        return CONNECTIONS;
    }
}

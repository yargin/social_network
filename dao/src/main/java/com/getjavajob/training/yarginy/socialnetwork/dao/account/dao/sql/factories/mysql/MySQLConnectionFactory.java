package com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories.mysql;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories.AbstractConnectionFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories.AbstractDMLQueriesFactory;

public class MySQLConnectionFactory extends AbstractConnectionFactory {
    public AbstractDMLQueriesFactory getDMLQueriesFactory() {
        return new MySQLDMLQueriesFactory(this);
    }

    @Override
    protected String getPropertiesFile() {
        return "connections/MySQLConnection.properties";
    }
}

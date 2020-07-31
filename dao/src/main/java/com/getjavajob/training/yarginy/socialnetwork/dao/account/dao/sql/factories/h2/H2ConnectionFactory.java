package com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories.h2;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories.AbstractConnectionFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories.AbstractDMLQueriesFactory;

public class H2ConnectionFactory extends AbstractConnectionFactory {
    @Override
    public AbstractDMLQueriesFactory getDMLQueriesFactory() {
        return new H2DMLQueriesFactory(this);
    }

    @Override
    protected String getPropertiesFile() {
        return "connections/H2Connection.properties";
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.factories.h2.H2ConnectionFactory;

public class MainFactory {
    private static final AbstractConnectionFactory CONNECTION_FACTORY = new H2ConnectionFactory();
    private static final AbstractDMLQueriesFactory DML_QUERIES_FACTORY = CONNECTION_FACTORY.getDMLQueriesFactory();

    public static AbstractConnectionFactory getConnectionFactory() {
        return CONNECTION_FACTORY;
    }

    public static AbstractDMLQueriesFactory getDmlQueriesFactory() {
        return DML_QUERIES_FACTORY;
    }
}

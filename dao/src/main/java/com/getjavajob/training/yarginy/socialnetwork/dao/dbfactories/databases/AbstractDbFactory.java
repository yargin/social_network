package com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.databases;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.AccountDAO;
import com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.AccountDAOImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DmlQueriesExecutor;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.implementations.DbConnectorImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.implementations.DmlQueriesExecutorImpl;

public abstract class AbstractDbFactory implements DbFactory {
    private final DbConnector dbConnector = new DbConnectorImpl(getConnectionPropertiesFile());

    /**
     * gives file name storing {@link java.util.Properties} for connection
     *
     * @return {@link String} representation of file name
     */
    protected abstract String getConnectionPropertiesFile();

    /**
     * gives file name storing DML queries
     *
     * @return {@link String} representation of file name
     */
    protected abstract String getDmlQueriesFile();

    @Override
    public DmlQueriesExecutor getDmlQueriesExecutor() {
        return new DmlQueriesExecutorImpl(dbConnector, getDmlQueriesFile());
    }

    @Override
    public AccountDAO getAccountDao() {
        return new AccountDAOImpl(dbConnector);
    }
}

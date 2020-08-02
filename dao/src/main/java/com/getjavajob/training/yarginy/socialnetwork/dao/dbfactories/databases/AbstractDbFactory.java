package com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.databases;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.account.dao.sql.AccountDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.DmlQueriesExecutor;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.implementations.DbConnectorImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories.implementations.DmlQueriesExecutorImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.GroupDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.dao.sql.GroupDaoImpl;

import java.util.Properties;

public abstract class AbstractDbFactory implements DbFactory {
    private final DbConnector dbConnector = new DbConnectorImpl(getConnectionPropertiesFile());

    /**
     * gives file name storing {@link Properties} for connection
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
    public AccountDao getAccountDao() {
        return new AccountDaoImpl(dbConnector);
    }

    @Override
    public GroupDao getGroupDao() {
        return new GroupDaoImpl(dbConnector);
    }
}

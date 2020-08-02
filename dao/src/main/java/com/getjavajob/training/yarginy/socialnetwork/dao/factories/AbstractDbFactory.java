package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.dml.DmlExecutor;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.dml.DmlExecutorImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnectorImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.dao.AccountDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.sql.AccountSql;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.GroupDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.dao.GroupDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.sql.GroupSql;

import java.util.Properties;

public abstract class AbstractDbFactory implements DbFactory {
    private final DbConnector dbConnector;

    public AbstractDbFactory() {
        dbConnector = new DbConnectorImpl(getConnectionFile());
    }

    /**
     * gives file name storing {@link Properties} for connection
     *
     * @return {@link String} representation of file name
     */
    protected abstract String getConnectionFile();

    protected abstract String getDmlDirectory();

    @Override
    public DmlExecutor getDmlExecutor() {
        return new DmlExecutorImpl(dbConnector, getDmlDirectory());
    }

    @Override
    public AccountDao getAccountDao() {
        return new AccountDaoImpl(dbConnector, new AccountSql());
    }

    @Override
    public GroupDao getGroupDao() {
        return new GroupDaoImpl(dbConnector, new GroupSql());
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.DaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.accounts.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.groups.GroupDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.pool2.DbConnectorImpl2;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl.ScriptExecutor;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl.ScriptExecutorImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.selfrelated.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.selfrelated.SelfManyToManyDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.selfrelated.friendship.FriendshipDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.variousrelated.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.variousrelated.ManyToManyDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.variousrelated.accountsingroups.AccountsInGroupsDml;

import java.util.Properties;

import static java.util.Objects.isNull;

public abstract class CommonDbFactory implements DbFactory {
    private final DbConnector dbConnector;
    private ScriptExecutor scriptExecutor;

    public CommonDbFactory() {
        dbConnector = DbConnectorImpl2.getDbConnector(getConnectionFile(), getConnectionsCapacity());
        if (runScriptOnStart()) {
            getScriptExecutor().executeScript(getStartingScript());
        }
    }

    /**
     * gives file name storing {@link Properties} for connection
     *
     * @return {@link String} representation of file name
     */
    protected abstract String getConnectionFile();

    /**
     * provides directory storing scripts. Used only to cut script file name. User free to leave it empty, but on-use
     * will have to specify path to script
     *
     * @return path to directory storing scripts
     */
    protected abstract String getScriptDirectory();

    /**
     * provides file with script that will be executed at right after factory creation if
     * {@link CommonDbFactory#runScriptOnStart()} returns true
     *
     * @return script file name
     */
    protected abstract String getStartingScript();

    /**
     * tells {@link CommonDbFactory} to execute starting script or not
     */
    protected abstract boolean runScriptOnStart();

    /**
     * specifies number of possible concurrent connections
     *
     * @return concurrent connections capacity
     */
    protected abstract int getConnectionsCapacity();

    @Override
    public ScriptExecutor getScriptExecutor() {
        if (isNull(scriptExecutor)) {
            scriptExecutor = new ScriptExecutorImpl(dbConnector, getScriptDirectory());
        }
        return scriptExecutor;
    }

    @Override
    public Dao<Account> getAccountDao() {
        return new DaoImpl<>(dbConnector, new AccountDml());
    }

    @Override
    public Dao<Group> getGroupDao() {
        return new DaoImpl<>(dbConnector, new GroupDml());
    }

    @Override
    public ManyToManyDao<Account, Group> getGroupMembershipDao() {
        return new ManyToManyDaoImpl<>(dbConnector, new AccountsInGroupsDml(), getAccountDao(), getGroupDao());
    }

    @Override
    public SelfManyToManyDao<Account> getFriendshipDao() {
        return new SelfManyToManyDaoImpl<>(dbConnector, new FriendshipDml(), getAccountDao());
    }
}

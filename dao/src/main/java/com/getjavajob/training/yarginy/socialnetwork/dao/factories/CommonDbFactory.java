package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnector;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.DbConnectorImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl.ScriptExecutor;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl.ScriptExecutorImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.dao.AccountDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.account.dml.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.GroupDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.dao.GroupDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.group.sql.GroupDml;

import java.util.Properties;

import static java.util.Objects.isNull;

public abstract class CommonDbFactory implements DbFactory {
    private final DbConnector dbConnector;
    private ScriptExecutor scriptExecutor;

    public CommonDbFactory() {
        dbConnector = new DbConnectorImpl(getConnectionFile(), getConnectionsCapacity());
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
    public AccountDao getAccountDao() {
        return new AccountDaoImpl(dbConnector, new AccountDml());
    }

    @Override
    public GroupDao getGroupDao() {
        return new GroupDaoImpl(dbConnector, new GroupDml());
    }
}

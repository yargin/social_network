package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.dmls.BatchGroupDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.dmls.BatchPhonesDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.dependedmodeldao.OwnedModelDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.dependedmodeldao.OwnedModelDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.dependedmodeldao.dmls.AccountPhotoDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPoolImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl.ScriptExecutor;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl.ScriptExecutorImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.DaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.password.PasswordDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.password.PasswordDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.friendship.FriendshipDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.accountsfriendshipsrequests.AccountsFriendshipsRequestsDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.groupmembers.GroupsMembersDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.groupmoderators.GroupModeratorsDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.groupsmembershiprequests.GroupsMembershipsRequestsDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.accountgroups.AccountsGroupsDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.accountsphones.AccountsPhonesDml;

import java.util.Properties;

import static java.util.Objects.isNull;

public abstract class CommonDbFactory implements DbFactory {
    private final ConnectionPool connectionPool;
    private ScriptExecutor scriptExecutor;

    public CommonDbFactory() {
        loadJDBCDriver();
        connectionPool = ConnectionPoolImpl.getConnectionPool(getConnectionFile(), getConnectionsCapacity());
        if (runScriptOnStart()) {
            getScriptExecutor().executeScript(getStartingScript());
        }
    }

    protected abstract void loadJDBCDriver();

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
            scriptExecutor = new ScriptExecutorImpl(connectionPool, getScriptDirectory());
        }
        return scriptExecutor;
    }

    @Override
    public Dao<Account> getAccountDao() {
        return new DaoImpl<>(connectionPool, new AccountDml());
    }

    @Override
    public BatchDao<Group> getGroupDao() {
        return new BatchDaoImpl<>(connectionPool, new BatchGroupDml());
    }

    @Override
    public ManyToManyDao<Account, Group> getGroupMembershipDao() {
        return new ManyToManyDaoImpl<>(connectionPool, new GroupsMembersDml());
    }

    @Override
    public SelfManyToManyDao<Account> getFriendshipDao() {
        return new SelfManyToManyDaoImpl<>(connectionPool, new FriendshipDml());
    }

    @Override
    public BatchDao<Phone> getPhoneDao() {
        return new BatchDaoImpl<>(connectionPool, new BatchPhonesDml());
    }

    @Override
    public OneToManyDao<Account, Phone> getAccountsPhones(Dao<Account> accountDao) {
        return new OneToManyDaoImpl<>(connectionPool, new AccountsPhonesDml());
    }

    @Override
    public Dao<Password> getPasswordDao() {
        return new PasswordDao(connectionPool, new PasswordDml());
    }

    @Override
    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    @Override
    public OneToManyDao<Account, Group> getAccountsOwnedGroupsDao(Dao<Account> accountDao) {
        return new OneToManyDaoImpl<>(connectionPool, new AccountsGroupsDml());
    }

    @Override
    public OwnedModelDao<Account, AccountPhoto> getAccountPhotoDao(Dao<Account> accountDao) {
        return new OwnedModelDaoImpl<>(connectionPool, new AccountPhotoDml(), accountDao);
    }

    @Override
    public ManyToManyDao<Account, Group> getGroupModerators() {
        return new ManyToManyDaoImpl<>(connectionPool, new GroupModeratorsDml());
    }

    @Override
    public ManyToManyDao<Account, Group> getGroupRequests() {
        return new ManyToManyDaoImpl<>(connectionPool, new GroupsMembershipsRequestsDml());
    }

    @Override
    public ManyToManyDao<Account, Account> getFriendshipRequests() {
        return new ManyToManyDaoImpl<>(connectionPool, new AccountsFriendshipsRequestsDml());
    }
}

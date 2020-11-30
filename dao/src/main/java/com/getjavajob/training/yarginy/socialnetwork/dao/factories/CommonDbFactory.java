package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.dmls.BatchGroupDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.dmls.BatchPhonesDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl.ScriptExecutor;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl.ScriptExecutorImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.DaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.AccountDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.DialogDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.messages.AccountWallMessageDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.messages.DialogMessageDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.messages.GroupWallMessageDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.password.PasswordDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.password.PasswordDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.otherdao.DataSelectsDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.dmls.FriendshipDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.dmls.AccountsFriendshipsRequestsDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.dmls.GroupsMembersDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.dmls.GroupsMembershipsRequestsDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.dmls.GroupsModeratorsDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.dmls.*;

import javax.sql.DataSource;

import static java.util.Objects.isNull;

public abstract class CommonDbFactory implements DbFactory {
    private final DataSource dataSource;
    private ScriptExecutor scriptExecutor;

    public CommonDbFactory(DataSource dataSource) {
        this.dataSource = dataSource;
        if (runScriptOnStart()) {
            getScriptExecutor().executeScript(getStartingScript());
        }
    }

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

    @Override
    public ScriptExecutor getScriptExecutor() {
        if (isNull(scriptExecutor)) {
            scriptExecutor = new ScriptExecutorImpl(dataSource, getScriptDirectory());
        }
        return scriptExecutor;
    }

    @Override
    public Dao<Account> getAccountDao() {
        return new DaoImpl<>(dataSource, new AccountDml());
    }

    @Override
    public BatchDao<Group> getGroupDao() {
        return new BatchDaoImpl<>(dataSource, new BatchGroupDml());
    }

    @Override
    public ManyToManyDao<Account, Group> getGroupMembershipDao() {
        return new ManyToManyDaoImpl<>(dataSource, new GroupsMembersDml());
    }

    @Override
    public SelfManyToManyDao<Account> getFriendshipDao() {
        return new SelfManyToManyDaoImpl<>(dataSource, new FriendshipDml());
    }

    @Override
    public BatchDao<Phone> getPhoneDao() {
        return new BatchDaoImpl<>(dataSource, new BatchPhonesDml());
    }

    @Override
    public OneToManyDao<Phone> getAccountsPhones(Dao<Account> accountDao) {
        return new OneToManyDaoImpl<>(dataSource, new AccountPhonesDml());
    }

    @Override
    public Dao<Password> getPasswordDao() {
        return new PasswordDao(dataSource, new PasswordDml());
    }

    @Override
    public OneToManyDao<Group> getAccountsOwnedGroupsDao(Dao<Account> accountDao) {
        return new OneToManyDaoImpl<>(dataSource, new AccountGroupsDml());
    }

    @Override
    public ManyToManyDao<Account, Group> getGroupModeratorsDao() {
        return new ManyToManyDaoImpl<>(dataSource, new GroupsModeratorsDml());
    }

    @Override
    public ManyToManyDao<Account, Group> getGroupRequestsDao() {
        return new ManyToManyDaoImpl<>(dataSource, new GroupsMembershipsRequestsDml());
    }

    @Override
    public ManyToManyDao<Account, Account> getFriendshipRequestsDao() {
        return new ManyToManyDaoImpl<>(dataSource, new AccountsFriendshipsRequestsDml());
    }

    @Override
    public DataSelectsDao getDataSetsDao() {
        return new DataSelectsDao(dataSource);
    }

    @Override
    public Dao<Message> getAccountWallMessageDao() {
        return new DaoImpl<>(dataSource, new AccountWallMessageDml());
    }

    @Override
    public OneToManyDao<Message> getAccountWallMessagesDao() {
        return new OneToManyDaoImpl<>(dataSource, new AccountWallMessagesDml());
    }

    @Override
    public Dao<Message> getDialogMessageDao() {
        return new DaoImpl<>(dataSource, new DialogMessageDml());
    }

    @Override
    public OneToManyDao<Message> getDialogsMessagesDao() {
        return new OneToManyDaoImpl<>(dataSource, new DialogsMessagesDml());
    }

    @Override
    public Dao<Message> getGroupWallMessageDao() {
        return new DaoImpl<>(dataSource, new GroupWallMessageDml());
    }

    @Override
    public OneToManyDao<Message> getGroupWallMessagesDao() {
        return new OneToManyDaoImpl<>(dataSource, new GroupWallMessagesDml());
    }

    @Override
    public Dao<Dialog> getDialogDao() {
        return new DaoImpl<>(dataSource, new DialogDml());
    }

    @Override
    public OneToManyDao<Dialog> getAccountDialogsDao() {
        return new OneToManyDaoImpl<>(dataSource, new AccountDialogsDml());
    }
}

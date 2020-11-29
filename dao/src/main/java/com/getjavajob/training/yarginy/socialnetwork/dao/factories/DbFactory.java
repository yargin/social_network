package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.dialog.Dialog;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl.ScriptExecutor;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.otherdao.DataSelectsDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;

/**
 * stored entities abstract fabric. CRUD operations with entities are provided by *Dao objects
 */
public interface DbFactory {
    Dao<Account> getAccountDao();

    BatchDao<Group> getGroupDao();

    SelfManyToManyDao<Account> getFriendshipDao();

    ManyToManyDao<Account, Group> getGroupMembershipDao();

    BatchDao<Phone> getPhoneDao();

    OneToManyDao<Phone> getAccountsPhones(Dao<Account> accountDao);

    Dao<Password> getPasswordDao();

    ScriptExecutor getScriptExecutor();

    ConnectionPool getConnectionPool();

    OneToManyDao<Group> getAccountsOwnedGroupsDao(Dao<Account> accountDao);

    ManyToManyDao<Account, Group> getGroupModeratorsDao();

    ManyToManyDao<Account, Group> getGroupRequestsDao();

    ManyToManyDao<Account, Account> getFriendshipRequestsDao();

    DataSelectsDao getDataSetsDao();

    Dao<Message> getAccountWallMessageDao();

    OneToManyDao<Message> getAccountWallMessagesDao();

    Dao<Message> getDialogMessageDao();

    OneToManyDao<Message> getDialogsMessagesDao();

    Dao<Message> getGroupWallMessageDao();

    OneToManyDao<Message> getGroupWallMessagesDao();

    Dao<Dialog> getDialogDao();

    OneToManyDao<Dialog> getAccountDialogsDao();
}

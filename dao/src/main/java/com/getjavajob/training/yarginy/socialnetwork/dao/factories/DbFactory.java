package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.ConnectionPool;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connector.TransactionManager;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl.ScriptExecutor;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;

/**
 * stored entities abstract fabric. CRUD operations with entities are provided by *Dao objects
 */
public interface DbFactory {
    Dao<Account> getAccountDao();

    Dao<Group> getGroupDao();

    SelfManyToManyDao<Account> getFriendshipDao(Dao<Account> accountDao);

    ManyToManyDao<Account, Group> getGroupMembershipDao(Dao<Account> accountDao, Dao<Group> groupDao);

    Dao<Phone> getPhoneDao();

    OneToManyDao<Account, Phone> getAccountsPhones(Dao<Account> accountDao, Dao<Phone> phoneDao);

    Dao<Password> getPasswordDao();

    ScriptExecutor getScriptExecutor();

    BatchDao<Phone> getBatchPhoneDao();

    TransactionManager getTransactionManager();

    ConnectionPool getConnectionPool();

    OneToManyDao<Account, Group> getAccountsOwnedGroupsDao(Dao<Account> accountDao, Dao<Group> groupDao);
}

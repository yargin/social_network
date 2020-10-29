package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.accountphoto.AccountPhoto;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.password.Password;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.dependedmodeldao.OwnedModelDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.connectionpool.ConnectionPool;
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

    BatchDao<Group> getGroupDao();

    SelfManyToManyDao<Account> getFriendshipDao(Dao<Account> accountDao);

    ManyToManyDao<Account, Group> getGroupMembershipDao(Dao<Account> accountDao, BatchDao<Group> groupDao);

    BatchDao<Phone> getPhoneDao();

    OneToManyDao<Account, Phone> getAccountsPhones(Dao<Account> accountDao, BatchDao<Phone> phoneDao);

    Dao<Password> getPasswordDao();

    ScriptExecutor getScriptExecutor();

    ConnectionPool getConnectionPool();

    OneToManyDao<Account, Group> getAccountsOwnedGroupsDao(Dao<Account> accountDao, BatchDao<Group> groupDao);

    OwnedModelDao<Account, AccountPhoto> getAccountPhotoDao(Dao<Account> accountDao);

    ManyToManyDao<Account, Group> getGroupModerators(Dao<Account> accountDao, BatchDao<Group> groupDao);
}

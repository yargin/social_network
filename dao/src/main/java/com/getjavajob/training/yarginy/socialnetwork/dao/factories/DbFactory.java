package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.entities.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl.ScriptExecutor;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.selfrelated.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.variousrelated.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.onetomany.OneToManyDao;

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

    ScriptExecutor getScriptExecutor();
}

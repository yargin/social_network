package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.EntityDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.ddl.ScriptExecutor;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.nonidentifying.SelfManyToManyDao;

/**
 * stored entities abstract fabric. CRUD operations with entities are provided by *Dao objects
 */
public interface DbFactory {
    EntityDao<Account> getAccountDao();

    EntityDao<Group> getGroupDao();

    SelfManyToManyDao<Account> getFriendshipDao();

    ScriptExecutor getScriptExecutor();
}

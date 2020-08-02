package com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.account.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.GroupDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.group.Group;

/**
 * provides SQL & DML operations bounded to specified database
 */
public interface DbFactory {
    /**
     * provides DML queries executor
     *
     * @return {@link DmlQueriesExecutor} that executes DML queries to database
     */
    DmlQueriesExecutor getDmlQueriesExecutor();

    /**
     * provides {@link AccountDao} to execute CRUD operations onto {@link Account}
     *
     * @return {@link AccountDao} implementation bounded to specified database
     */
    AccountDao getAccountDao();

    /**
     * provides {@link GroupDao} to execute CRUD operations onto {@link Group}
     *
     * @return {@link GroupDao} implementation bounded to specified database
     */
    GroupDao getGroupDao();
}

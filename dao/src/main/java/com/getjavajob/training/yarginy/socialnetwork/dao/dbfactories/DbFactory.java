package com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories;

import com.getjavajob.training.yarginy.socialnetwork.dao.account.AccountDAO;
import com.getjavajob.training.yarginy.socialnetwork.dao.account.Account;

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
     * provides {@link AccountDAO} to execute CRUD operations onto {@link Account}
     *
     * @return {@link AccountDAO} implementation bounded to specified database
     */
    AccountDAO getAccountDao();
}

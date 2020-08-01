package com.getjavajob.training.yarginy.socialnetwork.dao.dbfactories;

/**
 * provides DML queries to database, usually stored in some files
 */
public interface DmlQueriesExecutor {
    /**
     * creates table Accounts
     *
     * @return true if creation successful, otherwise false
     */
    boolean createAccounts();

    /**
     * deletes table Accounts
     *
     * @return true if deletion successful, otherwise false
     */
    boolean dropAccounts();
}

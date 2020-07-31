package com.getjavajob.training.yarginy.socialnetwork.dao.account;

/**
 * provides CRUD operations with {@link Account}
 */
public interface AccountDAO {
    /**
     * retrieves {@link Account} specified by id
     *
     * @param id {@link Account}'s number
     * @return {@link Account} that was found or {@link Account#getNullAccount()} if any wasn't found
     */
    Account selectAccount(int id);

    /**
     * retrieves {@link Account} specified by email
     *
     * @param email {@link Account}'s email
     * @return {@link Account} that was found or {@link Account#getNullAccount()} if any wasn't found
     */
    Account selectAccount(String email);

    /**
     * creates specified {@link Account}
     *
     * @param account {@link Account} to create
     * @return true if successfully created, false if specified {@link Account} already exists
     */
    boolean createAccount(Account account);

    /**
     * updates specified {@link Account}
     *
     * @param account {@link Account} to update
     * @return true if successfully updated, false if specified {@link Account} doesn't exist
     */
    boolean updateAccount(Account account);

    /**
     * deletes specified {@link Account}
     *
     * @param account {@link Account} to delete
     * @return true if successfully deleted, false if specified {@link Account} doesn't exist
     */
    boolean deleteAccount(Account account);
}

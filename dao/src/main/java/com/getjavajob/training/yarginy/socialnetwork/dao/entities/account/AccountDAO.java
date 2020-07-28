package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account;

public interface AccountDAO {
    AccountDTO selectAccount(int id);

    AccountDTO selectAccount(String email);

    boolean createAccount(AccountDTO account);

    boolean updateAccount(AccountDTO account);

    boolean deleteAccount(AccountDTO account);
}

package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account;

public interface AccountDAO {
    Account retrieveAccount(int id);

    Account retrieveAccount(String email);

    boolean createAccount(Account account);

    boolean updateAccount(Account account);

    boolean deleteAccount(Account account);
}

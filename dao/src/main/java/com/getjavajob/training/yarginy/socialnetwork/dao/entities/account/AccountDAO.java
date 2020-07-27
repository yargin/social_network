package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account;

public interface AccountDAO {
    Account getAccount(int id);

    Account getAccount(String email);

    boolean insertAccount(Account account);

    boolean updateAccount(Account account);

    boolean deleteAccount(Account account);
}

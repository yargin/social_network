package com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.dao;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.entity.Account;

import java.sql.SQLException;

public interface AccountDAO {
    Account getAccount(int id) throws SQLException;

    Account getAccount(String email) throws SQLException;

    boolean insertAccount(Account account) throws SQLException;

    boolean updateAccount(Account account) throws SQLException;

    boolean deleteAccount(Account account) throws SQLException;
}

package com.getjavajob.trainig.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.dao.entities.EntityDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.account.Account;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class AccountServiceImpl {
    EntityDao<Account> accountDao = getDbFactory().getAccountDao();

    public boolean createAccount(Account account) {
        return accountDao.create(account);
    }

    public boolean updateAccount(Account account) {
        return accountDao.update(account);
    }

    public boolean deleteAccount(Account account) {
        return false;
    }

    public boolean addFriend(Account account, Account friend) {
        return false;
    }

    public boolean removeFriend(Account account, Account friend) {
        return false;
    }
}

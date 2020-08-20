package com.getjavajob.trainig.yarginy.socialnetwork.service;


import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.selfrelated.SelfManyToManyDao;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;
import static java.util.Objects.isNull;

public class AccountServiceImpl implements AccountService {
    private final DbFactory dbFactory;
    private final Dao<Account> accountDao;
    private final SelfManyToManyDao<Account> friendshipDao;
    private Collection<Account> friends;

    public AccountServiceImpl() {
        dbFactory = getDbFactory();
        accountDao = dbFactory.getAccountDao();
        friendshipDao = dbFactory.getFriendshipDao();
    }

    public AccountServiceImpl(Dao<Account> accountDao, SelfManyToManyDao<Account> friendshipDao) {
        dbFactory = null;
        this.accountDao = accountDao;
        this.friendshipDao = friendshipDao;
    }

    public Account getAccount(int id) {
        return accountDao.select(id);
    }

    public Account getAccount(String email) {
        return accountDao.select(email);
    }

    public boolean createAccount(Account account) {
        return accountDao.create(account);
    }

    public boolean updateAccount(Account account) {
        return accountDao.update(account);
    }

    public boolean deleteAccount(Account account) {
        return accountDao.delete(account);
    }

    public boolean addFriend(Account account, Account friend) {
        if (friendshipDao.create(account, friend)) {
            if (!isNull(friends) && !friends.add(friend)) {
                friends = null;
            }
            return true;
        }
        return false;
    }

    public boolean removeFriend(Account account, Account friend) {
        if (friendshipDao.delete(account, friend)) {
            if (!isNull(friends) && !friends.remove(friend)) {
                friends = null;
            }
            return true;
        }
        return false;
    }

    public Collection<Account> getFriends(Account account) {
        if (isNull(friends)) {
            friends = friendshipDao.select(account);
        }
        return friends;
    }
}

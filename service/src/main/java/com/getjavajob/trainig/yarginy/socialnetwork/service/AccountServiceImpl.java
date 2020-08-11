package com.getjavajob.trainig.yarginy.socialnetwork.service;


import com.getjavajob.training.yarginy.socialnetwork.common.entities.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.entities.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.factories.DbFactory;
import com.getjavajob.training.yarginy.socialnetwork.dao.relations.manytomany.selfrelated.SelfManyToManyDao;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;
import static java.util.Objects.isNull;

public class AccountServiceImpl implements AccountService {
    private final DbFactory dbFactory = getDbFactory();
    private final Dao<Account> accountDao = dbFactory.getAccountDao();
    private final SelfManyToManyDao<Account> friendshipDao = dbFactory.getFriendshipDao();
    private Collection<Account> friends;

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
            friends.add(friend);
            return true;
        }
        return false;
    }

    public boolean removeFriend(Account account, Account friend) {
        if (friendshipDao.delete(account, friend)) {
            friends.remove(friend);
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

    private boolean validateEmail(String email) {
        email.trim().toLowerCase();
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9] + [a-zA-Z0-9_.]+ + [a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

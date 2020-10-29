package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDao;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class AccountsFriendshipsDaoImpl implements AccountsFriendshipsDao {
    private final Dao<Account> accountDao = getDbFactory().getAccountDao();
    private final SelfManyToManyDao<Account> friendshipDao = getDbFactory().getFriendshipDao(accountDao);

    @Override
    public Collection<Account> selectFriends(Account account) {
        return friendshipDao.select(account);
    }

    @Override
    public boolean createFriendship(Account firstAccount, Account secondAccount) {
        return friendshipDao.create(firstAccount, secondAccount);
    }

    @Override
    public boolean removeFriendship(Account firstAccount, Account secondAccount) {
        return friendshipDao.delete(firstAccount, secondAccount);
    }
}

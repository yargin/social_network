package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class AccountDaoImpl implements AccountDao {
    private final Dao<Account> accountDao = getDbFactory().getAccountDao();
    private final BatchDao<Group> groupDao = getDbFactory().getGroupDao();
    private final OneToManyDao<Account, Group> accountGroupsDao = getDbFactory().getAccountsOwnedGroupsDao(accountDao,
            groupDao);
    private final ManyToManyDao<Account, Group> accountsInGroupsDao = getDbFactory().getGroupMembershipDao(accountDao,
            groupDao);
    private final OneToManyDao<Account, Phone> accountPhonesDao = getDbFactory().getAccountsPhones(accountDao,
            getDbFactory().getPhoneDao());
    private final SelfManyToManyDao<Account> accountFriendsDao = getDbFactory().getFriendshipDao(accountDao);

    @Override
    public Account select(long id) {
        return accountDao.select(id);
    }

    @Override
    public Account select(Account account) {
        return accountDao.select(account);
    }

    @Override
    public boolean create(Account account) {
        return accountDao.create(account);
    }

    @Override
    public boolean update(Account account, Account storedAccount) {
        return accountDao.update(account, storedAccount);
    }

    @Override
    public boolean delete(Account account) {
        return accountDao.delete(account);
    }

    @Override
    public Collection<Account> selectAll() {
        return accountDao.selectAll();
    }

    @Override
    public Account getNullAccount() {
        return accountDao.getNullEntity();
    }

    @Override
    public Collection<Group> getOwnedGroups(Account account) {
        return accountGroupsDao.selectMany(account);
    }

    @Override
    public Collection<Group> getGroupMembership(Account account) {
        return accountsInGroupsDao.selectByFirst(account);
    }

    @Override
    public Collection<Phone> getPhones(Account account) {
        return accountPhonesDao.selectMany(account);
    }

    @Override
    public Collection<Account> getFriends(Account account) {
        return accountFriendsDao.select(account);
    }
}

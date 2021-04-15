package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.selfmanytomany.SelfManyToManyDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("accountDaoFacade")
public class AccountDaoFacadeImpl implements AccountDaoFacade {
    private final Dao<Account> accountDao;
    private final OneToManyDao<Group> accountGroupsDao;
    private final OneToManyDao<Phone> accountPhonesDao;
    private final ManyToManyDao<Account, Group> accountsInGroupsDao;
    private final SelfManyToManyDao<Account> accountFriendsDao;

    public AccountDaoFacadeImpl(@Qualifier("jpaAccountDao") Dao<Account> accountDao,
                                OneToManyDao<Group> accountGroupsDao, OneToManyDao<Phone> accountPhonesDao,
                                @Qualifier("jpaGroupMembershipDao") ManyToManyDao<Account, Group> accountsInGroupsDao,
                                @Qualifier("jpaFriendshipDao") SelfManyToManyDao<Account> accountFriendsDao) {
        this.accountDao = accountDao;
        this.accountGroupsDao = accountGroupsDao;
        this.accountPhonesDao = accountPhonesDao;
        this.accountsInGroupsDao = accountsInGroupsDao;
        this.accountFriendsDao = accountFriendsDao;
    }

    @Override
    public Account select(long id) {
        return accountDao.select(id);
    }

    @Override
    public Account select(Account account) {
        return accountDao.select(account);
    }

    @Override
    public Account getNullModel() {
        return accountDao.getNullModel();
    }

    @Override
    public boolean create(Account account) {
        return accountDao.create(account);
    }

    @Override
    public boolean update(Account account) {
        return accountDao.update(account);
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
    public Collection<Group> getOwnedGroups(long accountId) {
        return accountGroupsDao.selectMany(accountId);
    }

    @Override
    public Collection<Group> getGroupMembership(long accountId) {
        return accountsInGroupsDao.selectByFirst(accountId);
    }

    @Override
    public Collection<Phone> getPhones(long accountId) {
        return accountPhonesDao.selectMany(accountId);
    }

    @Override
    public Collection<Account> getFriends(long accountId) {
        return accountFriendsDao.select(accountId);
    }
}

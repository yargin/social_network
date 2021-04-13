package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.relationsdao.manytomany.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.relationsdao.manytomany.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.relationsdao.onetomany.OneToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.JpaAccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.JpaDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.JpaPhoneDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.manytomany.JpaGroupMembershipDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.manytomany.JpaManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.onetomany.JpaAccountGroups;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.onetomany.JpaAccountPhones;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.onetomany.JpaOneToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.selfmanytomany.JpaFriendshipDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationsdao.selfmanytomany.JpaSelfManyToManyDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("accountDaoFacade")
public class AccountDaoFacadeImpl implements AccountDaoFacade {
    private final JpaDao<Account> accountDao;
    private final JpaOneToManyDao<Group> accountGroupsDao;
    private final JpaOneToManyDao<Phone> accountPhonesDao;
    private final JpaManyToManyDao<Account, Group> accountsInGroupsDao;
    private final JpaSelfManyToManyDao<Account> accountFriendsDao;

    public AccountDaoFacadeImpl(@Qualifier("jpaAccountDao") JpaDao<Account> accountDao,
                                JpaOneToManyDao<Group> accountGroupsDao, JpaOneToManyDao<Phone> accountPhonesDao,
                                @Qualifier("jpaGroupMembershipDao") JpaManyToManyDao<Account, Group> accountsInGroupsDao,
                                @Qualifier("jpaFriendshipDao") JpaSelfManyToManyDao<Account> accountFriendsDao) {
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
    public boolean update(Account account, Account storedAccount) {
//        return accountDao.update(account, storedAccount);
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

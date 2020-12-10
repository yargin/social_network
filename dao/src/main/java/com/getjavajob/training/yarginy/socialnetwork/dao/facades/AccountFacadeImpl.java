package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.phone.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("accountDaoFacade")
public class AccountFacadeImpl implements AccountFacade {
    private Dao<Account> accountDao;
    private OneToManyDao<Group> accountGroupsDao;
    private ManyToManyDao<Account, Group> accountsInGroupsDao;
    private OneToManyDao<Phone> accountPhonesDao;
    private SelfManyToManyDao<Account> accountFriendsDao;

    @Autowired
    public void setAccountDao(Dao<Account> accountDao) {
        this.accountDao = accountDao;
    }

    @Autowired
    public void setAccountGroupsDao(@Qualifier("accountOwnerGroupsDao") OneToManyDao<Group> accountGroupsDao) {
        this.accountGroupsDao = accountGroupsDao;
    }

    @Autowired
    public void setAccountsInGroupsDao(@Qualifier("groupMembershipDao") ManyToManyDao<Account, Group> accountsInGroupsDao) {
        this.accountsInGroupsDao = accountsInGroupsDao;
    }

    @Autowired
    public void setAccountPhonesDao(@Qualifier("accountPhonesDao") OneToManyDao<Phone> accountPhonesDao) {
        this.accountPhonesDao = accountPhonesDao;
    }

    @Autowired
    public void setAccountFriendsDao(SelfManyToManyDao<Account> accountFriendsDao) {
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
    public Account getNullEntity() {
        return accountDao.getNullEntity();
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

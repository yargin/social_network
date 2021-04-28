package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.modeldaos.implementations.NewAccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.manytomany.implementations.NewGroupMembershipDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.implementations.NewAccountPhonesDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.implementations.NewOwnerGroupsDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.selfmanytomany.implementations.NewFriendshipsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("accountDaoFacade")
public class AccountDaoFacadeImpl implements AccountDaoFacade {
    private NewAccountDao accountDao;
    private NewOwnerGroupsDao accountGroupsDao;
    private NewAccountPhonesDao accountPhonesDao;
    private NewGroupMembershipDao accountsInGroupsDao;
    private NewFriendshipsDao accountFriendsDao;
    private TransactionPerformer transactionPerformer;

    @Autowired
    public void setAccountDao(NewAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Autowired
    public void setAccountGroupsDao(NewOwnerGroupsDao accountGroupsDao) {
        this.accountGroupsDao = accountGroupsDao;
    }

    @Autowired
    public void setAccountPhonesDao(NewAccountPhonesDao accountPhonesDao) {
        this.accountPhonesDao = accountPhonesDao;
    }

    @Autowired
    public void setAccountsInGroupsDao(NewGroupMembershipDao accountsInGroupsDao) {
        this.accountsInGroupsDao = accountsInGroupsDao;
    }

    @Autowired
    public void setAccountFriendsDao(NewFriendshipsDao accountFriendsDao) {
        this.accountFriendsDao = accountFriendsDao;
    }

    @Autowired
    public void setTransactionPerformer(TransactionPerformer transactionPerformer) {
        this.transactionPerformer = transactionPerformer;
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
        return transactionPerformer.transactionPerformed(accountDao::create, account);
    }

    @Override
    public boolean update(Account account) {
        return transactionPerformer.transactionPerformed(accountDao::update, account);
    }

    @Override
    public boolean delete(Account account) {
        return transactionPerformer.transactionPerformed(accountDao::delete, account);
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

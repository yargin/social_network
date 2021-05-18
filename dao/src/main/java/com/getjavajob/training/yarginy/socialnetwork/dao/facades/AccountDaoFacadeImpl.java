package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Phone;
import com.getjavajob.training.yarginy.socialnetwork.common.models.additionaldata.Role;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations.GroupMembershipDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.onetomany.implementations.AccountPhonesDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.selfmanytomany.implementations.FriendshipsDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.repositories.RepoGroupDao;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("accountDaoFacade")
public class AccountDaoFacadeImpl implements AccountDaoFacade {
    private final AccountDao accountDao;
    private final RepoGroupDao repoGroupDao;
    private final AccountPhonesDao accountPhonesDao;
    private final GroupMembershipDao accountsInGroupsDao;
    private final FriendshipsDao accountFriendsDao;
    private final TransactionPerformer transactionPerformer;

    public AccountDaoFacadeImpl(AccountDao accountDao, AccountPhonesDao accountPhonesDao,
                                GroupMembershipDao accountsInGroupsDao, FriendshipsDao accountFriendsDao,
                                TransactionPerformer transactionPerformer, RepoGroupDao repoGroupDao) {
        this.accountDao = accountDao;
        this.accountPhonesDao = accountPhonesDao;
        this.accountsInGroupsDao = accountsInGroupsDao;
        this.accountFriendsDao = accountFriendsDao;
        this.transactionPerformer = transactionPerformer;
        this.repoGroupDao = repoGroupDao;
    }

    @Override
    public Account select(long id) {
        return accountDao.select(id);
    }

    @Override
    public Account selectFullInfo(long id) {
        return accountDao.selectFullInfo(id);
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
    public boolean setRole(Account account, Role role) {
        return transactionPerformer.transactionPerformed(accountDao::setRole, account, role);
    }

    @Override
    public Collection<Account> selectAll() {
        return accountDao.selectAll();
    }

    @Override
    public Collection<Group> getOwnedGroups(long accountId) {
        return repoGroupDao.findByOwner(new Account(accountId));
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

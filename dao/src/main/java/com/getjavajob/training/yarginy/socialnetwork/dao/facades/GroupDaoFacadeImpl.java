package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.modeldaos.implementations.NewGroupDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.manytomany.implementations.NewGroupMembershipDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.newdaos.relationdaos.onetomany.implementations.NewOwnerGroupsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("groupDaoFacade")
public class GroupDaoFacadeImpl implements GroupDaoFacade {
    private NewGroupDao groupDao;
    private NewOwnerGroupsDao accountsOwnedGroupsDao;
    private NewGroupMembershipDao accountsGroupMembershipDao;
    private TransactionPerformer transactionPerformer;

    @Autowired
    public void setGroupDao(NewGroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Autowired
    public void setAccountsOwnedGroupsDao(NewOwnerGroupsDao accountsOwnedGroupsDao) {
        this.accountsOwnedGroupsDao = accountsOwnedGroupsDao;
    }

    @Autowired
    public void setAccountsGroupMembershipDao(NewGroupMembershipDao accountsGroupMembershipDao) {
        this.accountsGroupMembershipDao = accountsGroupMembershipDao;
    }

    @Autowired
    public void setTransactionPerformer(TransactionPerformer transactionPerformer) {
        this.transactionPerformer = transactionPerformer;
    }

    @Override
    public Group select(long id) {
        return groupDao.select(id);
    }

    @Override
    public Group select(Group group) {
        return groupDao.select(group);
    }

    @Override
    public Group getNullModel() {
        return groupDao.getNullModel();
    }

    @Override
    public boolean create(Group group) {
        return transactionPerformer.transactionPerformed(groupDao::create, group);
    }

    @Override
    public boolean update(Group group) {
        return transactionPerformer.transactionPerformed(groupDao::update, group);
    }

    @Override
    public boolean delete(Group group) {
        return transactionPerformer.transactionPerformed(groupDao::delete, group);
    }

    @Override
    public Collection<Group> selectAll() {
        return groupDao.selectAll();
    }

    @Override
    public Collection<Group> selectGroupsByOwner(long accountId) {
        return accountsOwnedGroupsDao.selectMany(accountId);
    }

    @Override
    public boolean isOwner(long accountId, long groupId) {
        return accountsOwnedGroupsDao.relationExists(accountId, groupId);
    }

    @Override
    public Group getNullGroup() {
        return groupDao.getNullModel();
    }

    @Override
    public Collection<Account> selectMembers(long groupId) {
        return accountsGroupMembershipDao.selectBySecond(groupId);
    }

    @Override
    public boolean addMember(long accountId, long groupId) {
        return transactionPerformer.transactionPerformed(accountsGroupMembershipDao::create, accountId, groupId);
    }

    @Override
    public boolean removeMember(long accountId, long groupId) {
        return transactionPerformer.transactionPerformed(accountsGroupMembershipDao::delete, accountId, groupId);
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.TransactionPerformer;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldaos.implementations.GroupDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.manytomany.implementations.GroupMembershipDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.onetomany.implementations.OwnerGroupsDao;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("groupDaoFacade")
public class GroupDaoFacadeImpl implements GroupDaoFacade {
    private final GroupDao groupDao;
    private final OwnerGroupsDao accountsOwnedGroupsDao;
    private final GroupMembershipDao accountsGroupMembershipDao;
    private final TransactionPerformer transactionPerformer;

    public GroupDaoFacadeImpl(GroupDao groupDao, OwnerGroupsDao accountsOwnedGroupsDao,
                              GroupMembershipDao accountsGroupMembershipDao, TransactionPerformer transactionPerformer) {
        this.groupDao = groupDao;
        this.accountsOwnedGroupsDao = accountsOwnedGroupsDao;
        this.accountsGroupMembershipDao = accountsGroupMembershipDao;
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

package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations.GroupMembershipDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations.GroupRequestsDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.utils.TransactionPerformer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component("groupsMembersDaoFacade")
public class GroupsMembersFacadeImpl implements GroupsMembersDaoFacade {
    private final GroupMembershipDao groupMembershipDao;
    private final GroupRequestsDao membershipRequestsDao;
    private final TransactionPerformer transactionPerformer;

    public GroupsMembersFacadeImpl(GroupMembershipDao groupMembershipDao, GroupRequestsDao membershipRequestsDao,
                                   TransactionPerformer transactionPerformer) {
        this.groupMembershipDao = groupMembershipDao;
        this.membershipRequestsDao = membershipRequestsDao;
        this.transactionPerformer = transactionPerformer;
    }

    @Override
    public Collection<Group> selectAccountGroups(long accountId) {
        return groupMembershipDao.selectByFirst(accountId);
    }

    @Override
    public Collection<Account> selectMembers(long groupId) {
        return groupMembershipDao.selectBySecond(groupId);
    }

    @Override
    public boolean joinGroup(long accountId, long groupId) {
        return transactionPerformer.perform(() -> groupMembershipDao.create(accountId, groupId));
    }

    @Override
    public boolean leaveGroup(long accountId, long groupId) {
        return transactionPerformer.perform(() -> groupMembershipDao.delete(accountId, groupId));
    }

    @Override
    public Collection<Account> selectRequests(long groupId) {
        return membershipRequestsDao.selectBySecond(groupId);
    }

    @Override
    public boolean isRequester(long accountId, long groupId) {
        return membershipRequestsDao.relationExists(accountId, groupId);
    }

    @Override
    public boolean createRequest(long accountId, long groupId) {
        return transactionPerformer.perform(() -> membershipRequestsDao.create(accountId, groupId));
    }

    @Override
    public boolean removeRequest(long accountId, long groupId) {
        return transactionPerformer.perform(() -> membershipRequestsDao.delete(accountId, groupId));
    }

    @Override
    public boolean isMember(long accountId, long groupId) {
        return groupMembershipDao.relationExists(accountId, groupId);
    }

    @Override
    public Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId) {
        return groupMembershipDao.selectUnJoinedGroupsAreRequested(accountId);
    }
}

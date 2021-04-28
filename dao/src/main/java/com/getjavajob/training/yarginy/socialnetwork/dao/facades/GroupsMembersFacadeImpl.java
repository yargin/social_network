package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.manytomany.implementations.GroupMembershipDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationdaos.manytomany.implementations.GroupRequestsDao;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("groupsMembersDaoFacade")
public class GroupsMembersFacadeImpl implements GroupsMembersDaoFacade {
    private final GroupMembershipDao groupMembershipDao;
    private final GroupRequestsDao membershipRequestsDao;

    public GroupsMembersFacadeImpl(GroupMembershipDao groupMembershipDao, GroupRequestsDao membershipRequestsDao) {
        this.groupMembershipDao = groupMembershipDao;
        this.membershipRequestsDao = membershipRequestsDao;
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
        try {
            groupMembershipDao.create(accountId, groupId);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean leaveGroup(long accountId, long groupId) {
        try {
            groupMembershipDao.delete(accountId, groupId);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
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
        try {
            membershipRequestsDao.create(accountId, groupId);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean removeRequest(long accountId, long groupId) {
        try {
            membershipRequestsDao.delete(accountId, groupId);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean isMember(long accountId, long groupId) {
        return groupMembershipDao.relationExists(accountId, groupId);
    }
}

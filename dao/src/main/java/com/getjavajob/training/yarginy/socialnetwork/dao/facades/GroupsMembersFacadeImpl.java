package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("groupsMembersDaoFacade")
public class GroupsMembersFacadeImpl implements GroupsMembersFacade {
    private final ManyToManyDao<Account, Group> groupMembershipDao;
    private final ManyToManyDao<Account, Group> membershipRequestsDao;

    @Autowired
    public GroupsMembersFacadeImpl(@Qualifier("groupMembershipDao") ManyToManyDao<Account, Group> groupMembershipDao,
                                   @Qualifier("groupRequestsDao") ManyToManyDao<Account, Group> membershipRequestsDao) {
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
        return groupMembershipDao.create(accountId, groupId);
    }

    @Override
    public boolean leaveGroup(long accountId, long groupId) {
        return groupMembershipDao.delete(accountId, groupId);
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
        return membershipRequestsDao.create(accountId, groupId);
    }

    @Override
    public boolean removeRequest(long accountId, long groupId) {
        return membershipRequestsDao.delete(accountId, groupId);
    }

    @Override
    public boolean isMember(long accountId, long groupId) {
        return groupMembershipDao.relationExists(accountId, groupId);
    }
}

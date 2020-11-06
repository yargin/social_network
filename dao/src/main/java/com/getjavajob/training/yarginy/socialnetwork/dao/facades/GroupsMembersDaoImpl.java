package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class GroupsMembersDaoImpl implements GroupsMembersDao {
    private final Dao<Account> accountDao = getDbFactory().getAccountDao();
    private final BatchDao<Group> groupDao = getDbFactory().getGroupDao();
    private final ManyToManyDao<Account, Group> groupMembershipDao = getDbFactory().getGroupMembershipDao(accountDao,
            groupDao);
    private final ManyToManyDao<Account, Group> membershipRequestsDao = getDbFactory().getGroupRequests(accountDao,
            groupDao);

    @Override
    public Collection<Group> selectAccountGroups(Account account) {
        return groupMembershipDao.selectByFirst(account);
    }

    @Override
    public Collection<Account> selectMembers(Group group) {
        return groupMembershipDao.selectBySecond(group);
    }

    @Override
    public boolean joinGroup(Account account, Group group) {
        return groupMembershipDao.create(account, group);
    }

    @Override
    public boolean leaveGroup(Account account, Group group) {
        return groupMembershipDao.delete(account, group);
    }

    @Override
    public Collection<Account> selectRequests(Group group) {
        return membershipRequestsDao.selectBySecond(group);
    }

    @Override
    public boolean createRequest(Account account, Group group) {
        return membershipRequestsDao.create(account, group);
    }

    @Override
    public boolean removeRequest(Account account, Group group) {
        return membershipRequestsDao.delete(account, group);
    }

    @Override
    public boolean isMember(long accountId, long groupId) {
        return groupMembershipDao.relationExists(accountId, groupId);
    }
}

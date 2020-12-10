package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("groupDaoFacade")
public class GroupFacadeImpl implements GroupFacade {
    private GroupFacade groupFacade;
    private OneToManyDao<Group> accountsOwnedGroupsDao;
    private ManyToManyDao<Account, Group> accountsGroupMembershipDao;

    @Autowired
    public void setGroupFacade(GroupFacade groupFacade) {
        this.groupFacade = groupFacade;
    }

    @Autowired
    public void setAccountsOwnedGroupsDao(@Qualifier("accountOwnerGroupsDao") OneToManyDao<Group> accountsOwnedGroupsDao) {
        this.accountsOwnedGroupsDao = accountsOwnedGroupsDao;
    }

    @Autowired
    public void setAccountsGroupMembershipDao(@Qualifier("groupMembershipDao") ManyToManyDao<Account, Group> accountsGroupMembershipDao) {
        this.accountsGroupMembershipDao = accountsGroupMembershipDao;
    }

    @Override
    public Group select(long id) {
        return groupFacade.select(id);
    }

    @Override
    public Group select(Group group) {
        return groupFacade.select(group);
    }

    @Override
    public Group getNullEntity() {
        return groupFacade.getNullEntity();
    }

    @Override
    public boolean create(Group group) {
        return groupFacade.create(group);
    }

    @Override
    public boolean update(Group group, Group storedGroup) {
        return groupFacade.update(group, storedGroup);
    }

    @Override
    public boolean delete(Group group) {
        return groupFacade.delete(group);
    }

    @Override
    public Collection<Group> selectAll() {
        return groupFacade.selectAll();
    }

    @Override
    public Collection<Group> selectGroupsByOwner(long accountId) {
        return accountsOwnedGroupsDao.selectMany(accountId);
    }

    @Override
    public boolean isOwner(long accountId, long groupId) {
        return accountsOwnedGroupsDao.relationExists(groupId, accountId);
    }

    @Override
    public Group getNullGroup() {
        return groupFacade.getNullEntity();
    }

    @Override
    public Collection<Account> selectMembers(long groupId) {
        return accountsGroupMembershipDao.selectBySecond(groupId);
    }

    @Override
    public boolean addMember(long accountId, long groupId) {
        return accountsGroupMembershipDao.create(accountId, groupId);
    }

    @Override
    public boolean removeMember(long accountId, long groupId) {
        return accountsGroupMembershipDao.delete(accountId, groupId);
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.models.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("groupDaoFacade")
public class GroupDaoFacadeImpl implements GroupDaoFacade {
    private final Dao<Group> groupDao;
    private final OneToManyDao<Group> accountsOwnedGroupsDao;
    private final ManyToManyDao<Account, Group> accountsGroupMembershipDao;

    public GroupDaoFacadeImpl(@Qualifier("jpaGroupDao") Dao<Group> groupDao,
                              OneToManyDao<Group> accountsOwnedGroupsDao,
                              @Qualifier("jpaGroupMembershipDao") ManyToManyDao<Account, Group>
                                      accountsGroupMembershipDao) {
        this.groupDao = groupDao;
        this.accountsOwnedGroupsDao = accountsOwnedGroupsDao;
        this.accountsGroupMembershipDao = accountsGroupMembershipDao;
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
        return groupDao.create(group);
    }

    @Override
    public boolean update(Group group, Group storedGroup) {

        return groupDao.update(group);
//        return groupDao.update(group, storedGroup);
    }

    @Override
    public boolean delete(Group group) {
        return groupDao.delete(group);
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
        return accountsGroupMembershipDao.create(accountId, groupId);
    }

    @Override
    public boolean removeMember(long accountId, long groupId) {
        return accountsGroupMembershipDao.delete(accountId, groupId);
    }
}

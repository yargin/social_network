package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class GroupDaoImpl implements GroupDao {
    private final BatchDao<Group> groupDao = getDbFactory().getGroupDao();
    private final Dao<Account> accountDao = getDbFactory().getAccountDao();
    private final OneToManyDao<Group> accountsOwnedGroupsDao = getDbFactory().getAccountsOwnedGroupsDao(
            accountDao);
    private final ManyToManyDao<Account, Group> accountsGroupMembershipDao = getDbFactory().getGroupMembershipDao();

    @Override
    public Group select(long id) {
        return groupDao.select(id);
    }

    @Override
    public Group select(Group group) {
        return groupDao.select(group);
    }

    @Override
    public Group getNullEntity() {
        return groupDao.getNullEntity();
    }

    @Override
    public boolean create(Group group) {
        return groupDao.create(group);
    }

    @Override
    public boolean update(Group group, Group storedGroup) {
        return groupDao.update(group, storedGroup);
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
        return accountsOwnedGroupsDao.relationExists(groupId, accountId);
    }

    @Override
    public Group getNullGroup() {
        return groupDao.getNullEntity();
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

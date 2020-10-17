package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany.OneToManyDao;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class GroupDaoImpl implements GroupDao {
    private final Dao<Group> groupDao = getDbFactory().getGroupDao();
    private final Dao<Account> accountDao = getDbFactory().getAccountDao();
    private final OneToManyDao<Account, Group> accountsOwnedGroupsDao = getDbFactory().getAccountsOwnedGroupsDao(
            accountDao, groupDao);
    private final ManyToManyDao<Account, Group> accountsGroupMembershipDao = getDbFactory().getGroupMembershipDao(accountDao,
            groupDao);

    @Override
    public Group select(long id) {
        return groupDao.select(id);
    }

    @Override
    public Group select(Group group) {
        return groupDao.select(group);
    }

    @Override
    public boolean create(Group group) {
        return groupDao.create(group);
    }

    @Override
    public boolean update(Group group) {
        return groupDao.update(group);
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
    public Collection<Group> selectGroupsByOwner(Account account) {
        return accountsOwnedGroupsDao.selectMany(account);
    }

    @Override
    public Group getNullGroup() {
        return groupDao.getNullEntity();
    }

    @Override
    public Collection<Account> selectMembers(Group group) {
        return accountsGroupMembershipDao.selectBySecond(group);
    }

    @Override
    public boolean addMember(Group group, Account account) {
        return accountsGroupMembershipDao.create(account, group);
    }

    @Override
    public boolean removeMember(Group group, Account account) {
        return accountsGroupMembershipDao.delete(account, group);
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.batchmodeldao.BatchDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.Dao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;

import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class GroupsModeratorsDaoImpl implements GroupsModeratorsDao {
    private final Dao<Account> accountDao = getDbFactory().getAccountDao();
    private final BatchDao<Group> groupDao = getDbFactory().getGroupDao();
    private final ManyToManyDao<Account, Group> groupModerators = getDbFactory().getGroupModerators(accountDao,
            groupDao);

    @Override
    public Collection<Account> selectModerators(Group group) {
        return groupModerators.selectBySecond(group);
    }

    @Override
    public boolean addGroupModerator(Account account, Group group) {
        return groupModerators.create(account, group);
    }

    @Override
    public boolean deleteGroupModerator(Account account, Group group) {
        return groupModerators.delete(account, group);
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("groupsModeratorsDaoFacade")
public class GroupsModeratorsDaoFacadeImpl implements GroupsModeratorsDaoFacade {
    private final ManyToManyDao<Account, Group> groupModerators;

    @Autowired
    public GroupsModeratorsDaoFacadeImpl(@Qualifier("groupModeratorsDao") ManyToManyDao<Account, Group> groupModerators) {
        this.groupModerators = groupModerators;
    }

    @Override
    public boolean isModerator(long accountId, long groupId) {
        return groupModerators.relationExists(accountId, groupId);
    }

    @Override
    public Collection<Account> selectModerators(long groupId) {
        return groupModerators.selectBySecond(groupId);
    }

    @Override
    public boolean addGroupModerator(long accountId, long groupId) {
        return groupModerators.create(accountId, groupId);
    }

    @Override
    public boolean deleteGroupModerator(long accountId, long groupId) {
        return groupModerators.delete(accountId, groupId);
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.implementations.GroupModeratorsDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.utils.TransactionPerformer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component("groupsModeratorsDaoFacade")
public class GroupsModeratorsDaoFacadeImpl implements GroupsModeratorsDaoFacade {
    private final GroupModeratorsDao groupModeratorsDao;
    private final TransactionPerformer transactionPerformer;

    public GroupsModeratorsDaoFacadeImpl(GroupModeratorsDao groupModeratorsDao,
                                         TransactionPerformer transactionPerformer) {
        this.groupModeratorsDao = groupModeratorsDao;
        this.transactionPerformer = transactionPerformer;
    }

    @Override
    public boolean isModerator(long accountId, long groupId) {
        return groupModeratorsDao.relationExists(accountId, groupId);
    }

    @Override
    public Collection<Account> selectModerators(long groupId) {
        return groupModeratorsDao.selectBySecond(groupId);
    }

    @Override
    public boolean addGroupModerator(long accountId, long groupId) {
        return transactionPerformer.transactionPerformed(groupModeratorsDao::create, accountId, groupId);
    }

    @Override
    public boolean deleteGroupModerator(long accountId, long groupId) {
        return transactionPerformer.transactionPerformed(groupModeratorsDao::delete, accountId, groupId);
    }

    @Override
    public Map<Account, Boolean> getGroupMembersAreModerators(long groupId) {
        return groupModeratorsDao.getGroupMembersAreModerators(groupId);
    }
}

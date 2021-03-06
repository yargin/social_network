package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface GroupsModeratorsDaoFacade extends Serializable {
    Collection<Account> selectModerators(long groupId);

    boolean addGroupModerator(long accountId, long groupId);

    boolean deleteGroupModerator(long accountId, long groupId);

    boolean isModerator(long accountId, long groupId);

    Map<Account, Boolean> getGroupMembersAreModerators(long groupId);
}

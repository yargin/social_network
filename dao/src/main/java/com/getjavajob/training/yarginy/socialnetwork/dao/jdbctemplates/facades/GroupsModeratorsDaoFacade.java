package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;

import java.io.Serializable;
import java.util.Collection;

public interface GroupsModeratorsDaoFacade extends Serializable {
    Collection<Account> selectModerators(long groupId);

    boolean addGroupModerator(long accountId, long groupId);

    boolean deleteGroupModerator(long accountId, long groupId);

    boolean isModerator(long accountId, long groupId);
}

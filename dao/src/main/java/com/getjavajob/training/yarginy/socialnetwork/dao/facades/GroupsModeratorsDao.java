package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;

import java.util.Collection;

public interface GroupsModeratorsDao {
    Collection<Account> selectModerators(long groupId);

    boolean addGroupModerator(long accountId, long groupId);

    boolean deleteGroupModerator(long accountId, long groupId);

    boolean isModerator(long accountId, long groupId);
}

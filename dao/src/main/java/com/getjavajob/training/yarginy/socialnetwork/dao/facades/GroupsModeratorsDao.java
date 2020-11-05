package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;

import java.util.Collection;

public interface GroupsModeratorsDao {
    Collection<Account> selectModerators(Group group);

    boolean addGroupModerator(Account account, Group group);

    boolean deleteGroupModerator(Account account, Group group);

    boolean isModerator(Account account, Group group);
}

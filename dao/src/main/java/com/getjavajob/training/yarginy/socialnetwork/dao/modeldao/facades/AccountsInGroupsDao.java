package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;

import java.util.Collection;

public interface AccountsInGroupsDao {
    Collection<Group> selectAccountGroups(Account account);

    Collection<Account> selectMembers(Group second);

    boolean joinGroup(Account account, Group second);

    boolean leaveGroup(Account account, Group second);
}

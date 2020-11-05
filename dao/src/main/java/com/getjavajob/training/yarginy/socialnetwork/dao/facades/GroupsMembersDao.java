package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;

import java.util.Collection;

public interface GroupsMembersDao {
    Collection<Group> selectAccountGroups(Account account);

    Collection<Account> selectMembers(Group second);

    boolean joinGroup(Account account, Group second);

    boolean leaveGroup(Account account, Group second);

    Collection<Account> selectRequests(Group group);

    boolean createRequest(Account account, Group group);

    boolean removeRequest(Account account, Group group);

    boolean isMember(Account account, Group group);
}

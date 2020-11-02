package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;

import java.util.Collection;

public interface GroupService {
    Group selectGroup(Group group);

    Group selectGroup(long id);

    Collection<Group> getAccountGroups(Account account);

    Collection<Group> getAllGroups();

    Collection<Group> getNonJoinedGroups(Account account);

    boolean joinGroup(Account account, Group group);

    boolean leaveGroup(Account account, Group group);

    boolean sendGroupRequest(Account account, Group group);

    Collection<Account> getGroupRequests(Group group);

    Collection<Account> getModerators(Group group);

    boolean addModerator(Account account, Group group);

    boolean removeModerator(Account account, Group group);

    boolean createGroup(Group group);

    boolean removeGroup(Group group);

    boolean updateGroup(Group group, Group storedGroup);
}

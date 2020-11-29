package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface GroupService extends Serializable {
    Group get(Group group);

    Group get(long id);

    Collection<Group> getAccountGroups(long accountId);

    Collection<Group> getAllGroups();

    boolean isOwner(long accountId, long groupId);

    boolean acceptRequest(long accountId, long groupId);

    boolean declineRequest(long accountId, long groupId);

    boolean leaveGroup(long accountId, long groupId);

    boolean isMembershipRequester(long accountId, long groupId);

    boolean sendGroupRequest(long accountId, long groupId);

    Collection<Account> getGroupRequests(long groupId);

    Collection<Account> getModerators(long groupId);

    boolean isModerator(long accountId, long groupId);

    boolean addModerator(long accountId, long groupId);

    boolean removeModerator(long accountId, long groupId);

    boolean isMember(long accountId, long groupId);

    boolean createGroup(Group group);

    boolean removeGroup(Group group);

    boolean updateGroup(Group group, Group storedGroup);

    Map<Account, Boolean> getGroupMembersModerators(long groupId);

    Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId);
}

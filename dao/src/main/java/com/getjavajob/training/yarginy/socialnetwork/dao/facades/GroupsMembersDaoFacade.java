package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;

import java.io.Serializable;
import java.util.Collection;

public interface GroupsMembersDaoFacade extends Serializable {
    Collection<Group> selectAccountGroups(long accountId);

    Collection<Account> selectMembers(long groupId);

    boolean joinGroup(long accountId, long groupId);

    boolean leaveGroup(long accountId, long groupId);

    Collection<Account> selectRequests(long groupId);

    boolean isRequester(long accountId, long groupId);

    boolean createRequest(long accountId, long groupId);

    boolean removeRequest(long accountId, long groupId);

    boolean isMember(long accountId, long groupId);
}

package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;

import java.util.Map;

public interface DataSetsDao {
    Map<Account, Boolean> getGroupMembersAreModerators(long groupId);

    Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId);
}

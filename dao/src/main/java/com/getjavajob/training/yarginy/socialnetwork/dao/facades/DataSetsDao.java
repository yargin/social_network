package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;

import java.io.Serializable;
import java.util.Map;

public interface DataSetsDao extends Serializable {
    Map<Account, Boolean> getGroupMembersAreModerators(long groupId);

    Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId);

    SearchableDto searchAccountsGroups(String searchString, int pageNumber);
}

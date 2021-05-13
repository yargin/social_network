package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchablesDto;

import java.io.Serializable;
import java.util.Map;

public interface DataSetsDaoFacade extends Serializable {
    Map<Account, Boolean> getGroupMembersAreModerators(long groupId);

    Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId);

    SearchablesDto searchAccountsGroups(String searchString, int pageNumber, int limit);
}

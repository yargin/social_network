package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchablesDto;

public interface SearchDaoFacade {
    SearchablesDto searchAccountsGroups(String searchString, int pageNumber, int limit);
}

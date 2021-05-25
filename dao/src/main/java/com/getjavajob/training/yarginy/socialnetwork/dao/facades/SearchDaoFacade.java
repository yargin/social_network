package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchablesDto;

import java.io.Serializable;

public interface SearchDaoFacade extends Serializable {
    SearchablesDto searchAccountsGroups(String searchString, int pageNumber, int limit);
}

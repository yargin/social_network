package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;

public interface DataSetsService {
    SearchableDto searchAccountsGroups(String searchString, int pageNumber);
}

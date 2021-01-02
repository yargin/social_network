package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;

import java.io.Serializable;

public interface DataSetsService extends Serializable {
    SearchableDto searchAccountsGroups(String searchString, int pageNumber, int limit);
}

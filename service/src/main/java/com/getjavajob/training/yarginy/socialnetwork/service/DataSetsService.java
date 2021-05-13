package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchablesDto;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public interface DataSetsService extends Serializable {
    SearchablesDto searchAccountsGroups(String searchString, int pageNumber, int limit);
}

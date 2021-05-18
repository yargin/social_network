package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchablesDto;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.SearchDaoFacade;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {
    private final SearchDaoFacade searchDaoFacade;

    public SearchServiceImpl(SearchDaoFacade searchDaoFacade) {
        this.searchDaoFacade = searchDaoFacade;
    }

    @Override
    public SearchablesDto searchAccountsGroups(String searchString, int pageNumber, int limit) {
        return searchDaoFacade.searchAccountsGroups(searchString, pageNumber, limit);
    }
}

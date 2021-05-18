package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchablesDto;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.modeldaos.implementations.GlobalSearchDao;
import org.springframework.stereotype.Repository;

@Repository("searchDaoFacade")
public class SearchDaoFacadeImpl implements SearchDaoFacade {
    private final GlobalSearchDao globalSearchDao;

    public SearchDaoFacadeImpl(GlobalSearchDao globalSearchDao) {
        this.globalSearchDao = globalSearchDao;
    }

    @Override
    public SearchablesDto searchAccountsGroups(String searchString, int pageNumber, int limit) {
        return globalSearchDao.searchAccountsGroups(searchString, pageNumber, limit);
    }
}

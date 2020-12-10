package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DataSetsFacade;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DataSetsFacadeImpl;

public class DataSetsServiceImpl implements DataSetsService {
    private final DataSetsFacade dataSetsFacade = new DataSetsFacadeImpl();

    @Override
    public SearchableDto searchAccountsGroups(String searchString, int pageNumber) {
        return dataSetsFacade.searchAccountsGroups(searchString, pageNumber);
    }
}

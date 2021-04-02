package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.facades.DataSetsDaoFacade;
import org.springframework.stereotype.Service;

@Service
public class DataSetsServiceImpl implements DataSetsService {
    private final DataSetsDaoFacade dataSetsDaoFacade;

    public DataSetsServiceImpl(DataSetsDaoFacade dataSetsDaoFacade) {
        this.dataSetsDaoFacade = dataSetsDaoFacade;
    }

    @Override
    public SearchableDto searchAccountsGroups(String searchString, int pageNumber, int limit) {
        return dataSetsDaoFacade.searchAccountsGroups(searchString, pageNumber, limit);
    }
}

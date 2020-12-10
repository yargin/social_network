package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DataSetsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSetsServiceImpl implements DataSetsService {
    private final DataSetsFacade dataSetsFacade;

    @Autowired
    public DataSetsServiceImpl(DataSetsFacade dataSetsFacade) {
        this.dataSetsFacade = dataSetsFacade;
    }

    @Override
    public SearchableDto searchAccountsGroups(String searchString, int pageNumber) {
        return dataSetsFacade.searchAccountsGroups(searchString, pageNumber);
    }
}

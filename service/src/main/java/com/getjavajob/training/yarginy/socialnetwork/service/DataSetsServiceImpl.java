package com.getjavajob.training.yarginy.socialnetwork.service;

import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DataSetsDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.facades.DataSetsDaoImpl;

public class DataSetsServiceImpl implements DataSetsService {
    private final DataSetsDao dataSetsDao = new DataSetsDaoImpl();

    @Override
    public SearchableDto searchAccountsGroups(String searchString, int pageNumber) {
        return dataSetsDao.searchAccountsGroups(searchString, pageNumber);
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchablesDto;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbc.DataSetsDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jpa.relationdaos.manytomany.AdditionalManyToManyDao;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component("dataSetDaoFacade")
public class DataSetsDaoFacadeImpl implements DataSetsDaoFacade {
    private final DataSetsDao dataSetsDao;
    private final AdditionalManyToManyDao additionalManyToManyDao;

    public DataSetsDaoFacadeImpl(DataSetsDao dataSetsDao, AdditionalManyToManyDao additionalManyToManyDao) {
        this.dataSetsDao = dataSetsDao;
        this.additionalManyToManyDao = additionalManyToManyDao;
    }

    @Override
    public Map<Account, Boolean> getGroupMembersAreModerators(long groupId) {
        return additionalManyToManyDao.getGroupMembersAreModerators(groupId);
    }

    @Override
    public Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId) {
        return additionalManyToManyDao.selectUnJoinedGroupsAreRequested(accountId);
    }

    @Override
    public SearchablesDto searchAccountsGroups(String searchString, int pageNumber, int limit) {
        return dataSetsDao.searchAccountsGroups(searchString, pageNumber, limit);
    }
}

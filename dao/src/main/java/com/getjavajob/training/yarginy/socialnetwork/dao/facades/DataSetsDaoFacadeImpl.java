package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;
import com.getjavajob.training.yarginy.socialnetwork.dao.otherdao.DataSetsDao;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component("dataSetDaoFacade")
public class DataSetsDaoFacadeImpl implements DataSetsDaoFacade {
    private final DataSetsDao dataSetsDao;

    public DataSetsDaoFacadeImpl(DataSetsDao dataSetsDao) {
        this.dataSetsDao = dataSetsDao;
    }

    @Override
    public Map<Account, Boolean> getGroupMembersAreModerators(long groupId) {
        return dataSetsDao.getGroupMembersModerators(groupId);
    }

    @Override
    public Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId) {
        return dataSetsDao.getAllUnjoinedGroupsAreRequested(accountId);
    }

    @Override
    public SearchableDto searchAccountsGroups(String searchString, int pageNumber, int limit) {
        return dataSetsDao.searchAccountsGroups(searchString, pageNumber, limit);
    }
}

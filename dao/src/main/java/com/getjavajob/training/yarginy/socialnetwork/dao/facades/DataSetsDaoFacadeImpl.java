package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.common.models.searchable.SearchableDto;
import com.getjavajob.training.yarginy.socialnetwork.dao.otherdao.DataSelectsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component("dataSetDaoFacade")
public class DataSetsDaoFacadeImpl implements DataSetsDaoFacade {
    private final DataSelectsDao dataSelectsDao;

    @Autowired
    public DataSetsDaoFacadeImpl(DataSelectsDao dataSelectsDao) {
        this.dataSelectsDao = dataSelectsDao;
    }

    @Override
    public Map<Account, Boolean> getGroupMembersAreModerators(long groupId) {
        return dataSelectsDao.getGroupMembersModerators(groupId);
    }

    @Override
    public Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId) {
        return dataSelectsDao.getAllUnjoinedGroupsAreRequested(accountId);
    }

    @Override
    public SearchableDto searchAccountsGroups(String searchString, int pageNumber, int limit) {
        return dataSelectsDao.searchAccountsGroups(searchString, pageNumber, limit);
    }
}

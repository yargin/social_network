package com.getjavajob.training.yarginy.socialnetwork.dao.facades;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.otherdao.DataSelectsDao;

import java.util.Map;

import static com.getjavajob.training.yarginy.socialnetwork.dao.factories.AbstractDbFactory.getDbFactory;

public class DataSetsDaoImpl implements DataSetsDao {
    private final DataSelectsDao dataSelectsDao = getDbFactory().getDataSetsDao();

    @Override
    public Map<Account, Boolean> getGroupMembersAreModerators(long groupId) {
        return dataSelectsDao.getGroupMembersModerators(groupId);
    }

    @Override
    public Map<Group, Boolean> getAllUnjoinedGroupsAreRequested(long accountId) {
        return dataSelectsDao.getAllUnjoinedGroupsAreRequested(accountId);
    }
}

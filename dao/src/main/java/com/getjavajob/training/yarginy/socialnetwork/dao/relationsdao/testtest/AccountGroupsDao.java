package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest;

import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;

@Repository
public class AccountGroupsDao extends AbstractOneToManyDao<Group> {
    private static final String GROUP_ALIAS = "g";
    private final GroupDao groupDao;

    @Autowired
    public AccountGroupsDao(DataSource dataSource, GroupDao groupDao) {
        super(dataSource);
        this.groupDao = groupDao;
    }

    private String getSelectManyQuery() {
        return "SELECT " + groupDao.getViewFields(GROUP_ALIAS) + " FROM _groups g WHERE g.owner_id = ?";
    }

    @Override
    public Collection<Group> selectMany(long accountId) {
        String query = getSelectManyQuery();
        return template.query(query, groupDao.getSuffixedViewRowMapper(GROUP_ALIAS), accountId);
    }

    @Override
    protected String getSelectByBothQuery() {
        return "SELECT 1 FROM _groups g WHERE g.owner_id = ? AND g.id = ?";
    }

    @Override
    protected Object[] getBothParams(long accountId, long groupId) {
        return new Object[]{accountId, groupId};
    }
}

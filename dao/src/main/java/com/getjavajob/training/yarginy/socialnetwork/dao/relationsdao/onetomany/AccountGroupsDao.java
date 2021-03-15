package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("groupOwnersDao")
public class AccountGroupsDao implements OneToManyDao<Group> {
    private static final String GROUP_ALIAS = "g";
    private final GroupDao groupDao;
    private final transient JdbcTemplate template;

    @Autowired
    public AccountGroupsDao(JdbcTemplate template, GroupDao groupDao) {
        this.groupDao = groupDao;
        this.template = template;
    }

    @Override
    public Collection<Group> selectMany(long accountId) {
        String query = "SELECT " + groupDao.getViewFields(GROUP_ALIAS) + " FROM _groups g WHERE g.owner_id = ?";
        return template.query(query, groupDao.getSuffixedViewRowMapper(GROUP_ALIAS), accountId);
    }

    @Override
    public boolean relationExists(long accountId, long groupId) {
        String query = "SELECT 1 FROM _groups g WHERE g.owner_id = ? AND g.id = ?";
        try {
            template.queryForObject(query, new Object[]{accountId, groupId}, ((resultSet, i) -> resultSet.getInt(1)));
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}

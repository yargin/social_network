package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.GroupDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("groupOwnersDao")
public class AccountGroupsDao implements OneToManyDao<Group> {
    private static final String GROUP_ALIAS = "g";
    private final GroupDao groupDao;
    private final transient JdbcTemplate template;

    public AccountGroupsDao(JdbcTemplate template, GroupDao groupDao) {
        this.groupDao = groupDao;
        this.template = template;
    }

    @Override
    public Collection<Group> selectMany(long accountId) {
        String query = "SELECT " + groupDao.getViewFields(GROUP_ALIAS) + " FROM `GROUPS` g WHERE g.owner_id = ?";
        return template.query(query, groupDao.getSuffixedViewRowMapper(GROUP_ALIAS), accountId);
    }

    @Override
    public boolean relationExists(long accountId, long groupId) {
        String query = "SELECT 1 FROM `GROUPS` g WHERE g.owner_id = ? AND g.id = ?";
        try {
            template.queryForObject(query, new Object[]{accountId, groupId}, ((resultSet, i) -> resultSet.getInt(1)));
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Collection;

@Repository
public class GroupRequestsDao implements ManyToManyDao<Account, Group> {
    private static final String GROUP_ALIAS = "gr";
    private static final String ACC_ALIAS = "acc";
    private static final String TABLE = "groups_memberships_requests";
    private static final String ACCOUNT_ID = "account_id";
    private static final String GROUP_ID = "group_id";
    private final transient JdbcTemplate template;
    private final transient SimpleJdbcInsert insertTemplate;
    private final GroupDao groupDao;
    private final AccountDao accountDao;

    @Autowired
    public GroupRequestsDao(JdbcTemplate template, SimpleJdbcInsert insertTemplate, GroupDao groupDao,
                            AccountDao accountDao) {
        this.template = template;
        this.insertTemplate = insertTemplate;
        insertTemplate.withTableName(TABLE);
        this.groupDao = groupDao;
        this.accountDao = accountDao;
    }

    @Override
    public Collection<Group> selectByFirst(long accountId) {
        String query = "SELECT " + groupDao.getViewFields(GROUP_ALIAS) + " FROM _groups gr JOIN " +
                "groups_memberships_requests gr_m ON gr.id = gr_m.group_id WHERE gr_m.account_id = ?";
        return template.query(query, new Object[]{accountId}, groupDao.getSuffixedViewRowMapper(GROUP_ALIAS));
    }

    @Override
    public Collection<Account> selectBySecond(long groupId) {
        String query = "SELECT " + accountDao.getViewFields(ACC_ALIAS) + " FROM accounts acc JOIN " +
                "groups_memberships_requests gr_m ON acc.id = gr_m.account_id WHERE gr_m.group_id = ?";
        return template.query(query, new Object[]{groupId}, accountDao.getSuffixedViewRowMapper(ACC_ALIAS));
    }

    @Override
    public boolean relationExists(long accountId, long groupId) {
        String query = "SELECT 1 FROM groups_memberships_requests gr_m WHERE gr_m.account_id = ? AND gr_m.group_id = ?";
        try {
            template.queryForObject(query, new Object[]{accountId, groupId}, (resultSet, i) -> resultSet.getInt(1));
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean create(long accountId, long groupId) {
        try {
            return insertTemplate.execute(new MapSqlParameterSource().addValue(ACCOUNT_ID, accountId, Types.BIGINT).
                    addValue(GROUP_ID, groupId, Types.BIGINT)) == 1;
        } catch (DuplicateKeyException e) {
            return false;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean delete(long accountId, long groupId) {
        String query = "DELETE FROM groups_memberships_requests gr_m WHERE gr_m.account_id = ? AND gr_m.group_id = ?";
        return template.update(query, accountId, groupId) == 1;
    }
}

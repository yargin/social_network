package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest.manytomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.Collection;

public class GroupMembersDao extends AbstractManyToMany<Account, Group> {
    private final JdbcTemplate template;

    public GroupMembersDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(template);
    }

    @Override
    public Collection<Group> selectByFirst(long accountId) {
        return null;
    }

    @Override
    public Collection<Account> selectBySecond(long groupId) {
        return null;
    }

    @Override
    public boolean relationExists(long accountId, long groupId) {
        return false;
    }

    @Override
    public boolean create(long accountId, long groupId) {
        return false;
    }

    @Override
    public boolean delete(long accountId, long groupId) {
        return false;
    }
}

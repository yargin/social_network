package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.AccountDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository("accountWallMessageDao")
public class AccountWallMessageDao extends AbstractMessageDao {
    private static final String TABLE = "account_wall_messages";

    public AccountWallMessageDao(JdbcTemplate template, SimpleJdbcInsert jdbcInsert, NamedParameterJdbcTemplate
            namedTemplate, AccountDao accountDao) {
        super(template, jdbcInsert, namedTemplate, TABLE, accountDao);
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository("dialogMessageDao")
public class DialogMessageDao extends AbstractMessageDao {
    private static final String TABLE = "dialogs_messages";

    @Autowired
    public DialogMessageDao(JdbcTemplate template, SimpleJdbcInsert jdbcInsert, NamedParameterJdbcTemplate namedTemplate,
                            AccountDao accountDao) {
        super(template, jdbcInsert, namedTemplate, TABLE, accountDao);
    }
}

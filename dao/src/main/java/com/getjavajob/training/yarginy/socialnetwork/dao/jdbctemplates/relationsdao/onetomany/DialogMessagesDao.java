package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.messages.DialogMessageDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//@Repository("dialogMessagesDao")
public class DialogMessagesDao extends AbstractMessagesDao {
    public DialogMessagesDao(JdbcTemplate template, DialogMessageDao manyDao, AccountDao accountDao) {
        super(template, manyDao, accountDao);
    }
}

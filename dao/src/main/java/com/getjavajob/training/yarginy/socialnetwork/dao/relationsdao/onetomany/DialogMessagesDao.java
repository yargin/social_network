package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.messages.DialogMessageDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("dialogMessagesDao")
public class DialogMessagesDao extends AbstractMessagesDao {
    public DialogMessagesDao(JdbcTemplate template, DialogMessageDao manyDao, AccountDao accountDao) {
        super(template, manyDao, accountDao);
    }
}

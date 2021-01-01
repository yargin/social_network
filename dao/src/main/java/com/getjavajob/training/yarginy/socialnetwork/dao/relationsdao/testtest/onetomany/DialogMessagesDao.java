package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.messages.DialogMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DialogMessagesDao extends AbstractMessagesDao {
    @Autowired
    public DialogMessagesDao(JdbcTemplate template, DialogMessageDao manyDao) {
        super(template, manyDao);
    }
}

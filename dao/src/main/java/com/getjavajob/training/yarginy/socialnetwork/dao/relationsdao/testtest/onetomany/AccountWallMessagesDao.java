package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.messages.AccountWallMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountWallMessagesDao extends AbstractMessagesDao {
    @Autowired
    public AccountWallMessagesDao(JdbcTemplate template, AccountWallMessageDao manyDao) {
        super(template, manyDao);
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.messages.AccountWallMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("accountWallMessagesDao")
public class AccountWallMessagesDao extends AbstractMessagesDao {
    @Autowired
    public AccountWallMessagesDao(JdbcTemplate template, AccountWallMessageDao manyDao, AccountDao accountDao) {
        super(template, manyDao, accountDao);
    }
}

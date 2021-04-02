package com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.jdbctemplates.modeldao.messages.AccountWallMessageDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("accountWallMessagesDao")
public class AccountWallMessagesDao extends AbstractMessagesDao {
    public AccountWallMessagesDao(JdbcTemplate template, AccountWallMessageDao manyDao, AccountDao accountDao) {
        super(template, manyDao, accountDao);
    }
}

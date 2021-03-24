package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.messages.GroupWallMessageDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("groupWallMessagesDao")
public class GroupWallMessagesDao extends AbstractMessagesDao {
    public GroupWallMessagesDao(JdbcTemplate template, GroupWallMessageDao manyDao, AccountDao accountDao) {
        super(template, manyDao, accountDao);
    }
}

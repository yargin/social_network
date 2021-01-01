package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.messages.GroupWallMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GroupWallMessagesDao extends AbstractMessagesDao {
    @Autowired
    public GroupWallMessagesDao(JdbcTemplate template, GroupWallMessageDao manyDao) {
        super(template, manyDao);
    }
}

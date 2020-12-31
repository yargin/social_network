package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.messages.GroupWallMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class GroupWallMessagesDao extends AbstractMessagesDao {
    @Autowired
    public GroupWallMessagesDao(DataSource dataSource, GroupWallMessageDao manyDao) {
        super(dataSource, manyDao);
    }
}

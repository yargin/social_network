package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class GroupWallMessageDao extends AbstractMessageDao {
    private static final String TABLE = "group_wall_message";

    @Autowired
    public GroupWallMessageDao(DataSource dataSource, AccountDao accountDao) {
        super(dataSource, TABLE, accountDao);
    }
}

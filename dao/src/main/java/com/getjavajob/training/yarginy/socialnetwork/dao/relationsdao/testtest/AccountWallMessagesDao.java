package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.messages.AccountWallMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AccountWallMessagesDao extends AbstractMessagesDao {
    @Autowired
    public AccountWallMessagesDao(DataSource dataSource, AccountWallMessageDao manyDao) {
        super(dataSource, manyDao);
    }
}

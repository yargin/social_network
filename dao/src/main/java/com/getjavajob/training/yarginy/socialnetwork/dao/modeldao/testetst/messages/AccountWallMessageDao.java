package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class AccountWallMessageDao extends AbstractMessageDao {
    private static final String TABLE = "account_wall_messages";

    @Autowired
    public AccountWallMessageDao(DataSource dataSource, AccountDao accountDao) {
        super(dataSource, TABLE, accountDao);
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.messages;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class DialogMessageDao extends AbstractMessageDao {
    private static final String TABLE = "dialog_messages";

    @Autowired
    public DialogMessageDao(DataSource dataSource, AccountDao accountDao) {
        super(dataSource, TABLE, accountDao);
    }
}

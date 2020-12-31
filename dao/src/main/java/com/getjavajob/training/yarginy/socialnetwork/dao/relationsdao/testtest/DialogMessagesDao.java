package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest;

import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.messages.DialogMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class DialogMessagesDao extends AbstractMessagesDao {
    @Autowired
    public DialogMessagesDao(DataSource dataSource, DialogMessageDao manyDao) {
        super(dataSource, manyDao);
    }
}

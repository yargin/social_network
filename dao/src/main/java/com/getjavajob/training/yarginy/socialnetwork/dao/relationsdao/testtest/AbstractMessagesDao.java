package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.messages.AbstractMessageDao;

import javax.sql.DataSource;
import java.util.Collection;

public abstract class AbstractMessagesDao extends AbstractOneToManyDao<Message> {
    private static final String ALIAS = "mess";
    private static final String ACC_ALIAS = "acc";
    private final AbstractMessageDao messageDao;

    public AbstractMessagesDao(DataSource dataSource, AbstractMessageDao messageDao) {
        super(dataSource);
        this.messageDao = messageDao;
    }

    private String getSelectManyQuery() {
        return "SELECT " + messageDao.getViewFields(ALIAS) + " FROM " + messageDao.getTable(ALIAS) + " JOIN accounts as" +
                "acc ON mess.author_id = acc.id WHERE mess.receiver_id = ?";
    }

    @Override
    public Collection<Message> selectMany(long oneId) {
        String query = getSelectManyQuery();
        return template.query(query, messageDao.getSuffixedViewRowMapper(ALIAS, ACC_ALIAS), oneId);
    }

    @Override
    protected String getSelectByBothQuery() {
        return "SELECT " + messageDao.getViewFields(ALIAS) + " FROM " + messageDao.getTable(ALIAS) + " WHERE " +
                "mess.receiver_id = ? AND mess.id = ?";
    }

    @Override
    protected Object[] getBothParams(long oneId, long manyId) {
        return new Object[]{oneId, manyId};
    }
}

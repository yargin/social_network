package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.testtest.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.testetst.messages.AbstractMessageDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collection;
import java.util.Objects;

public abstract class AbstractMessagesDao extends AbstractOneToManyDao<Message> {
    protected final transient JdbcTemplate template;
    private static final String ALIAS = "mess";
    private static final String ACC_ALIAS = "acc";
    private final AbstractMessageDao messageDao;

    public AbstractMessagesDao(JdbcTemplate template, AbstractMessageDao messageDao) {
        this.messageDao = messageDao;
        this.template = template;
    }

    @Override
    public Collection<Message> selectMany(long accountId) {
        String query = "SELECT " + messageDao.getViewFields(ALIAS) + " FROM " + messageDao.getTable(ALIAS) +
                " JOIN accounts as" + "acc ON mess.author_id = acc.id WHERE mess.receiver_id = ?";
        return template.query(query, getManyRowMapper(), accountId);
    }

    @Override
    public boolean relationExists(long accountId, long manyId) {
        String query = "SELECT " + messageDao.getViewFields(ALIAS) + " FROM " + messageDao.getTable(ALIAS) + " WHERE " +
                "mess.receiver_id = ? AND mess.id = ?";
        return Objects.equals(template.queryForObject(query, new Object[]{accountId, manyId}, ((resultSet, i) ->
                resultSet.getInt(1))), 1);
    }

    private RowMapper<Message> getManyRowMapper() {
        return messageDao.getSuffixedViewRowMapper(ALIAS, ACC_ALIAS);
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.onetomany;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.messages.AbstractMessageDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collection;

public abstract class AbstractMessagesDao implements OneToManyDao<Message> {
    protected final transient JdbcTemplate template;
    private static final String ALIAS = "mess";
    private static final String ACC_ALIAS = "acc";
    private final AbstractMessageDao messageDao;
    private final AccountDao accountDao;

    public AbstractMessagesDao(JdbcTemplate template, AbstractMessageDao messageDao, AccountDao accountDao) {
        this.messageDao = messageDao;
        this.template = template;
        this.accountDao = accountDao;
    }

    @Override
    public Collection<Message> selectMany(long accountId) {
        String query = "SELECT " + messageDao.getViewFields(ALIAS) + ", " + accountDao.getViewFields(ACC_ALIAS) +
                " FROM " + messageDao.getTable(ALIAS) + " JOIN accounts as acc ON mess.author = acc.id WHERE " +
                "mess.receiver_id = ?";
        return template.query(query, getManyRowMapper(), accountId);
    }

    @Override
    public boolean relationExists(long accountId, long messageId) {
        String query = "SELECT " + messageDao.getViewFields(ALIAS) + " FROM " + messageDao.getTable(ALIAS) + " WHERE " +
                "mess.receiver_id = ? AND mess.id = ?";
        try {
            template.queryForObject(query, new Object[]{accountId, messageId}, ((resultSet, i) -> resultSet.getInt(1)));
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    private RowMapper<Message> getManyRowMapper() {
        return messageDao.getSuffixedViewRowMapper(ALIAS, ACC_ALIAS);
    }
}

package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.MessageImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AccountDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.ValuePlacer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.Types;

public abstract class AbstractMessageDao extends AbstractDao<Message> {
    private static final String ALIAS = "mes";
    private static final String AUTHOR_ALIAS = "acc";
    private static final String ID = "id";
    private static final String AUTHOR = "author";
    private static final String MESSAGE = "message";
    private static final String IMAGE = "image";
    private static final String RECEIVER_ID = "receiver_id";
    private static final String POSTED = "posted";
    private final String table;
    private final AccountDao accountDao;

    public AbstractMessageDao(JdbcTemplate template, SimpleJdbcInsert jdbcInsert, NamedParameterJdbcTemplate namedTemplate,
                              String table, AccountDao accountDao) {
        super(template, jdbcInsert, namedTemplate, table, ALIAS);
        this.table = table;
        this.accountDao = accountDao;
    }

    @Override
    public Message getNullEntity() {
        return NullEntitiesFactory.getNullMessage();
    }

    @Override
    protected Object[] getObjectAltKeys(Message message) {
        return new Object[]{message.getAuthor().getId(), message.getReceiverId(), message.getDate()};
    }

    @Override
    protected MapSqlParameterSource createEntityFieldsMap(Message message) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(AUTHOR, message.getAuthor().getId(), Types.BIGINT);
        parameters.addValue(MESSAGE, message.getText(), Types.VARCHAR);
        parameters.addValue(IMAGE, message.getImage(), Types.BLOB);
        parameters.addValue(RECEIVER_ID, message.getReceiverId(), Types.BIGINT);
        parameters.addValue(POSTED, message.getDate(), Types.TIMESTAMP);
        return parameters;
    }

    @Override
    protected ValuePlacer getValuePlacer(Message message, Message storedMessage) {
        ValuePlacer placer = new ValuePlacer(table);
        placer.addFieldIfDiffers(message::getAuthor, storedMessage::getAuthor, AUTHOR, Types.BIGINT, Account::getId);
        placer.addFieldIfDiffers(message::getText, storedMessage::getText, MESSAGE, Types.VARCHAR);
        placer.addFieldIfDiffers(message::getImage, storedMessage::getImage, IMAGE, Types.BLOB);
        placer.addFieldIfDiffers(message::getReceiverId, storedMessage::getReceiverId, RECEIVER_ID, Types.BIGINT);
        placer.addFieldIfDiffers(message::getDate, storedMessage::getDate, POSTED, Types.TIMESTAMP);

        placer.addKey(storedMessage::getId, ID, Types.BIGINT);
        return placer;
    }

    @Override
    protected Object[] getObjectPrimaryKeys(Message message) {
        return new Object[]{message.getId()};
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT " + getFields("mes") + ", " + accountDao.getViewFields("acc") + "FROM messages as mes JOIN" +
                " accounts as acc ON mes.author = acc.id";
    }

    public RowMapper<Message> getSuffixedViewRowMapper(String messageSuffix, String authorSuffix) {
        return (resultSet, i) -> {
            Account author = accountDao.getSuffixedViewRowMapper(authorSuffix).mapRow(resultSet, i);
            Message message = new MessageImpl();
            message.setAuthor(author);
            message.setId(resultSet.getLong(ID + messageSuffix));
            message.setText(resultSet.getString(MESSAGE + messageSuffix));
            message.setDate(resultSet.getTimestamp(POSTED + messageSuffix));
            message.setReceiverId(resultSet.getLong(RECEIVER_ID + messageSuffix));
            message.setImage(resultSet.getBytes(IMAGE + messageSuffix));
            return message;
        };
    }


    public RowMapper<Message> getSuffixedRowMapper(String messageSuffix, String authorSuffix) {
        return getSuffixedViewRowMapper(messageSuffix, authorSuffix);
    }

    @Override
    public RowMapper<Message> getViewRowMapper() {
        return getSuffixedViewRowMapper(ALIAS, AUTHOR_ALIAS);
    }

    @Override
    public RowMapper<Message> getRowMapper() {
        return getSuffixedRowMapper(ALIAS, AUTHOR_ALIAS);
    }

    @Override
    protected String[] getFieldsList() {
        return getViewFieldsList();
    }

    @Override
    protected String[] getViewFieldsList() {
        return new String[]{ID, AUTHOR, RECEIVER_ID, MESSAGE, POSTED, IMAGE};
    }

    @Override
    public String[] getPrimaryKeys() {
        return new String[]{ID};
    }

    @Override
    public String[] getAltKeys() {
        return new String[]{AUTHOR, RECEIVER_ID, POSTED};
    }
}

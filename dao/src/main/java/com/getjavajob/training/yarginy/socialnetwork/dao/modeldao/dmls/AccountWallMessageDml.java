package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.MessageImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullMessage;
import static com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountWallMessagesTable.*;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public class AccountWallMessageDml extends AbstractDml<Message> {
    private static final String SELECT_ALL = buildQuery().select(TABLE).build();
    private static final String SELECT_BY_ID = buildQuery().selectJoin(TABLE, AccountsTable.TABLE, AUTHOR, AccountsTable.
            ID).where(ID).build();
    private static final String SELECT_BY_ALT_KEY = buildQuery().selectJoin(TABLE, AccountsTable.TABLE, AUTHOR,
            AccountsTable.ID).where(AUTHOR).and(ACCOUNT_WALL_ID).and(POSTED).build();
    private static final String SELECT_UPDATE_BY_ID = buildQuery().select(TABLE).where(ID).build();
    private static final String SELECT_UPDATE_BY_ALT_KEY = buildQuery().select(TABLE).where(AUTHOR).and(ACCOUNT_WALL_ID).
            and(POSTED).build();


    @Override
    protected String getSelectById() {
        return SELECT_BY_ID;
    }

    @Override
    protected String getSelectAll() {
        return SELECT_ALL;
    }

    @Override
    protected String getSelectByAltKey() {
        return SELECT_BY_ALT_KEY;
    }

    @Override
    protected String getSelectForUpdateByAltKey() {
        return SELECT_UPDATE_BY_ALT_KEY;
    }

    @Override
    protected String getSelectForUpdateById() {
        return SELECT_UPDATE_BY_ID;
    }

    @Override
    protected void setAltKeyParams(PreparedStatement statement, Message entity) throws SQLException {
        statement.setLong(1, entity.getAuthor().getId());
        statement.setLong(2, entity.getReceiverId());
        statement.setTimestamp(3, entity.getDate());
    }

    @Override
    public Message selectViewFromRow(ResultSet resultSet) throws SQLException {
        Message message = new MessageImpl();
        message.setId(resultSet.getLong(ID));
        Account account = new AccountImpl();
        account.setId(resultSet.getLong(AccountsTable.ID));
        account.setName(resultSet.getString(AccountsTable.NAME));
        account.setSurname(resultSet.getString(AccountsTable.SURNAME));
        message.setAuthor(account);
        message.setDate(resultSet.getTimestamp(POSTED));
        message.setText(resultSet.getString(MESSAGE));
        message.setReceiverId(resultSet.getLong(ACCOUNT_WALL_ID));
        message.setImage(resultSet.getBytes(IMAGE));
        return message;
    }

    @Override
    public Message selectFromRow(ResultSet resultSet) throws SQLException {
        return selectViewFromRow(resultSet);
    }

    @Override
    public Collection<Message> selectEntities(ResultSet resultSet) throws SQLException {
        Collection<Message> messages = new ArrayList<>();
        while (resultSet.next()) {
            messages.add(selectViewFromRow(resultSet));
        }
        return messages;
    }

    @Override
    public void updateRow(ResultSet resultSet, Message entity, Message storedEntity) throws SQLException {
        updateFieldIfDiffers(entity::getId, storedEntity::getId, resultSet::updateLong, ID);
        updateFieldIfDiffers(entity.getAuthor()::getId, storedEntity.getAuthor()::getId, resultSet::updateLong, AUTHOR);
        updateFieldIfDiffers(entity::getText, storedEntity::getText, resultSet::updateString, MESSAGE);
        updateFieldIfDiffers(entity::getImage, storedEntity::getImage, resultSet::updateBytes, IMAGE);
        updateFieldIfDiffers(entity::getReceiverId, storedEntity::getReceiverId, resultSet::updateLong, ACCOUNT_WALL_ID);
        updateFieldIfDiffers(entity::getDate, storedEntity::getDate, resultSet::updateTimestamp, POSTED);
    }

    @Override
    public Message getNullEntity() {
        return getNullMessage();
    }
}

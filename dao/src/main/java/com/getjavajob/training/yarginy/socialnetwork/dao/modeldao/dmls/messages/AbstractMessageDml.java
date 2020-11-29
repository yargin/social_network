package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.account.AccountImpl;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.common.models.message.MessageImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.AccountsTable;
import com.getjavajob.training.yarginy.socialnetwork.dao.tables.messages.MessagesTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static com.getjavajob.training.yarginy.socialnetwork.common.models.NullEntitiesFactory.getNullMessage;
import static com.getjavajob.training.yarginy.socialnetwork.dao.utils.querybuilder.SqlQueryBuilder.buildQuery;

public abstract class AbstractMessageDml extends AbstractDml<Message> {
    private final MessagesTable table = getTable();
    private final String selectAll = buildQuery().select(table.table).build();
    private final String selectById = buildQuery().selectJoin(table.table, AccountsTable.TABLE, table.author,
            AccountsTable.ID).where(table.id).build();
    private final String selectByAltKey = buildQuery().selectJoin(table.table, AccountsTable.TABLE, table.author,
            AccountsTable.ID).where(table.author).and(table.receiverId).and(table.posted).build();
    private final String selectUpdateById = buildQuery().select(table.table).where(table.id).build();
    private final String selectUpdateByAltKey = buildQuery().select(table.table).where(table.author).and(
            table.receiverId).and(table.posted).build();

    protected abstract MessagesTable getTable();

    @Override
    protected String getSelectById() {
        return selectById;
    }

    @Override
    protected String getSelectAll() {
        return selectAll;
    }

    @Override
    protected String getSelectByAltKey() {
        return selectByAltKey;
    }

    @Override
    protected String getSelectForUpdateByAltKey() {
        return selectUpdateByAltKey;
    }

    @Override
    protected String getSelectForUpdateById() {
        return selectUpdateById;
    }
    @Override
    protected void setAltKeyParams(PreparedStatement statement, Message entity) throws SQLException {
        statement.setLong(1, entity.getAuthor().getId());
        statement.setLong(2, entity.getReceiverId());
        statement.setTimestamp(3, entity.getDate());
    }

    @Override
    public Message retrieveViewFromRow(ResultSet resultSet) throws SQLException {
        Message message = new MessageImpl();
        message.setId(resultSet.getLong(table.id));
        Account account = new AccountImpl();
        account.setId(resultSet.getLong(AccountsTable.ID));
        account.setName(resultSet.getString(AccountsTable.NAME));
        account.setSurname(resultSet.getString(AccountsTable.SURNAME));
        message.setAuthor(account);
        message.setDate(resultSet.getTimestamp(table.posted));
        message.setText(resultSet.getString(table.message));
        message.setReceiverId(resultSet.getLong(table.receiverId));
        message.setImage(resultSet.getBytes(table.image));
        return message;
    }

    @Override
    public Message retrieveFromRow(ResultSet resultSet) throws SQLException {
        return retrieveViewFromRow(resultSet);
    }

    @Override
    public Collection<Message> retrieveEntities(ResultSet resultSet) throws SQLException {
        Collection<Message> messages = new ArrayList<>();
        while (resultSet.next()) {
            messages.add(retrieveViewFromRow(resultSet));
        }
        return messages;
    }

    @Override
    public void updateRow(ResultSet resultSet, Message entity, Message storedEntity) throws SQLException {
        updateFieldIfDiffers(entity::getId, storedEntity::getId, resultSet::updateLong, table.id);
        updateFieldIfDiffers(entity.getAuthor()::getId, storedEntity.getAuthor()::getId, resultSet::updateLong,
                table.author);
        updateFieldIfDiffers(entity::getText, storedEntity::getText, resultSet::updateString, table.message);
        updateFieldIfDiffers(entity::getImage, storedEntity::getImage, resultSet::updateBytes, table.image);
        updateFieldIfDiffers(entity::getReceiverId, storedEntity::getReceiverId, resultSet::updateLong, table.receiverId);
        updateFieldIfDiffers(entity::getDate, storedEntity::getDate, resultSet::updateTimestamp, table.posted);
    }

    @Override
    public Message getNullEntity() {
        return getNullMessage();
    }
}

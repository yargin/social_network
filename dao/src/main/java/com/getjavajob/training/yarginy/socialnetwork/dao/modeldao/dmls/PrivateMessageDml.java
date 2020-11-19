package com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.dmls;

import com.getjavajob.training.yarginy.socialnetwork.common.models.message.Message;
import com.getjavajob.training.yarginy.socialnetwork.dao.modeldao.AbstractDml;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class PrivateMessageDml extends AbstractDml<Message> {

    @Override
    protected String getSelectById() {
        return null;
    }

    @Override
    protected String getSelectAll() {
        return null;
    }

    @Override
    protected String getSelectByAltKey() {
        return null;
    }

    @Override
    protected String getSelectForUpdateByAltKey() {
        return null;
    }

    @Override
    protected String getSelectForUpdateById() {
        return null;
    }

    @Override
    protected void setAltKeyParams(PreparedStatement statement, Message entity) throws SQLException {

    }

    @Override
    public Message selectFromRow(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public Message selectViewFromRow(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public Collection<Message> selectEntities(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public void updateRow(ResultSet resultSet, Message entity, Message storedEntity) throws SQLException {

    }

    @Override
    public Message getNullEntity() {
        return null;
    }
}
